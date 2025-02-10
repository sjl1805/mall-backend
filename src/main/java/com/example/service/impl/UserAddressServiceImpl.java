package com.example.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mapper.UserAddressMapper;
import com.example.model.dto.address.AddressPageQueryDTO;
import com.example.model.entity.UserAddress;
import com.example.model.enums.UserAddressStatusEnum;
import com.example.service.UserAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author 31815
 * @description 针对表【user_address(用户收货地址表)】的数据库操作Service实现
 * @createDate 2025-02-10 02:08:40
 */
@Service
@RequiredArgsConstructor
public class UserAddressServiceImpl extends ServiceImpl<UserAddressMapper, UserAddress>
        implements UserAddressService {

    private final UserAddressMapper userAddressMapper;

    @Override
    public IPage<AddressPageQueryDTO> getAddressPage(AddressPageQueryDTO query) {
        return userAddressMapper.selectAdminAddressList(query);
    }


    @Override
    @Transactional
    public boolean setDefaultAddress(Long userId, Long addressId) {
        // 先取消其他默认地址
        userAddressMapper.batchUpdateStatus(
                userAddressMapper.selectUserAddressList(userId)
                        .stream().map(UserAddress::getId).toList(),
                UserAddressStatusEnum.NOT_DEFAULT,
                userId
        );
        return userAddressMapper.updateDefaultStatus(userId, addressId, UserAddressStatusEnum.DEFAULT) > 0;
    }

    @Override
    public Optional<UserAddress> getDefaultAddress(Long userId) {
        return userAddressMapper.selectDefaultAddress(userId);
    }

    @Override
    @Transactional
    public boolean batchUpdateAddressStatus(List<Long> ids, UserAddressStatusEnum status, Long userId) {
        return userAddressMapper.batchUpdateStatus(ids, status, userId) > 0;
    }

    @Override
    public List<UserAddress> getUserAddressList(Long userId) {
        return userAddressMapper.selectUserAddressList(userId);
    }
}




