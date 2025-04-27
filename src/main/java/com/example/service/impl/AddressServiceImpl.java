package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.ResultCode;
import com.example.exception.BusinessException;
import com.example.mapper.AddressMapper;
import com.example.model.entity.Address;
import com.example.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 28619
 * @description 针对表【address(收货地址表)】的数据库操作Service实现
 * @createDate 2025-04-09 17:42:15
 */
@Service
@RequiredArgsConstructor
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address>
        implements AddressService {

    @Override
    @Cacheable(value = "address", key = "'user:' + #userId + ':addresses'")
    public List<Address> getUserAddresses(Long userId) {
        if (userId == null) {
            throw new BusinessException("用户ID不能为空", ResultCode.PARAM_ERROR);
        }

        LambdaQueryWrapper<Address> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Address::getUserId, userId);
        queryWrapper.orderByDesc(Address::getIsDefault); // 默认地址排在前面
        queryWrapper.orderByDesc(Address::getUpdateTime); // 按更新时间倒序

        return list(queryWrapper);
    }

    @Override
    @Cacheable(value = "address", key = "'user:' + #userId + ':default'")
    public Address getDefaultAddress(Long userId) {
        if (userId == null) {
            throw new BusinessException("用户ID不能为空", ResultCode.PARAM_ERROR);
        }

        LambdaQueryWrapper<Address> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Address::getUserId, userId);
        queryWrapper.eq(Address::getIsDefault, 1); // 查询默认地址

        return getOne(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "address", allEntries = true)
    public Address addAddress(Address address, Long userId) {
        if (address == null || userId == null) {
            throw new BusinessException("参数错误", ResultCode.PARAM_ERROR);
        }

        // 设置用户ID
        address.setUserId(userId);

        // 如果是默认地址，则将其他地址设为非默认
        if (address.getIsDefault() != null && address.getIsDefault() == 1) {
            setOtherAddressesNonDefault(userId);
        }

        // 如果是第一个地址，则自动设为默认地址
        long count = count(new LambdaQueryWrapper<Address>().eq(Address::getUserId, userId));
        if (count == 0) {
            address.setIsDefault(1);
        }

        // 保存地址
        save(address);

        return address;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "address", allEntries = true)
    public boolean updateAddress(Address address, Long userId) {
        if (address == null || address.getId() == null || userId == null) {
            throw new BusinessException("参数错误", ResultCode.PARAM_ERROR);
        }

        // 验证地址是否属于该用户
        Address existAddress = getById(address.getId());
        if (existAddress == null) {
            throw new BusinessException("地址不存在", ResultCode.NOT_FOUND);
        }

        if (!existAddress.getUserId().equals(userId)) {
            throw new BusinessException("无权修改该地址", ResultCode.FORBIDDEN);
        }

        // 如果是设为默认地址，则将其他地址设为非默认
        if (address.getIsDefault() != null && address.getIsDefault() == 1) {
            setOtherAddressesNonDefault(userId);
        }

        // 更新地址
        address.setUserId(userId); // 防止修改用户ID
        return updateById(address);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "address", allEntries = true)
    public boolean deleteAddress(Long addressId, Long userId) {
        if (addressId == null || userId == null) {
            throw new BusinessException("参数错误", ResultCode.PARAM_ERROR);
        }

        // 验证地址是否属于该用户
        Address existAddress = getById(addressId);
        if (existAddress == null) {
            throw new BusinessException("地址不存在", ResultCode.NOT_FOUND);
        }

        if (!existAddress.getUserId().equals(userId)) {
            throw new BusinessException("无权删除该地址", ResultCode.FORBIDDEN);
        }

        // 如果删除的是默认地址，则需要设置新的默认地址
        if (existAddress.getIsDefault() == 1) {
            // 删除当前地址
            boolean result = removeById(addressId);

            if (result) {
                // 查找用户的其他地址
                LambdaQueryWrapper<Address> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(Address::getUserId, userId);
                queryWrapper.orderByDesc(Address::getUpdateTime);
                queryWrapper.last("LIMIT 1");

                Address newDefaultAddress = getOne(queryWrapper);
                if (newDefaultAddress != null) {
                    // 将第一个地址设为默认地址
                    newDefaultAddress.setIsDefault(1);
                    updateById(newDefaultAddress);
                }
            }

            return result;
        } else {
            // 删除非默认地址
            return removeById(addressId);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "address", allEntries = true)
    public boolean setDefaultAddress(Long addressId, Long userId) {
        if (addressId == null || userId == null) {
            throw new BusinessException("参数错误", ResultCode.PARAM_ERROR);
        }

        // 验证地址是否属于该用户
        Address existAddress = getById(addressId);
        if (existAddress == null) {
            throw new BusinessException("地址不存在", ResultCode.NOT_FOUND);
        }

        if (!existAddress.getUserId().equals(userId)) {
            throw new BusinessException("无权操作该地址", ResultCode.FORBIDDEN);
        }

        // 先将所有地址设为非默认
        setOtherAddressesNonDefault(userId);

        // 将当前地址设为默认
        existAddress.setIsDefault(1);
        return updateById(existAddress);
    }

    /**
     * 将用户的所有地址设为非默认
     *
     * @param userId 用户ID
     */
    private void setOtherAddressesNonDefault(Long userId) {
        LambdaUpdateWrapper<Address> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Address::getUserId, userId);
        updateWrapper.set(Address::getIsDefault, 0);
        update(updateWrapper);
    }
}




