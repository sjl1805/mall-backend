package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mapper.UserAddressMapper;
import com.example.model.entity.UserAddress;
import com.example.service.UserAddressService;
import org.springframework.stereotype.Service;
import com.example.exception.PermissionDeniedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.example.model.entity.UserAddressStatusEnum;

/**
 * @author 31815
 * @description 针对表【user_address(用户收货地址表)】的数据库操作Service实现
 * @createDate 2025-02-10 02:08:40
 */
@Service
public class UserAddressServiceImpl extends ServiceImpl<UserAddressMapper, UserAddress>
        implements UserAddressService {

    @Autowired
    private UserAddressMapper userAddressMapper;

    public void updateDefaultStatus(Long userId, Long addressId) {
        UserAddress address = userAddressMapper.selectById(addressId);
        if (address == null || !address.getUserId().equals(userId)) {
            throw new PermissionDeniedException("地址不存在或无权操作");
        }
        @Transactional
        userAddressMapper.updateDefaultStatus(userId, addressId, UserAddressStatusEnum.DEFAULT);
    }
}




