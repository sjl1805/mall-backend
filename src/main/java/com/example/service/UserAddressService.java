package com.example.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.model.dto.address.AddressPageQueryDTO;
import com.example.model.entity.UserAddress;
import com.example.model.enums.UserAddressStatusEnum;


import java.util.List;
import java.util.Optional;

/**
 * @author 31815
 * @description 针对表【user_address(用户收货地址表)】的数据库操作Service
 * @createDate 2025-02-10 02:08:40
 */
public interface UserAddressService extends IService<UserAddress> {
    IPage<AddressPageQueryDTO> getAddressPage(AddressPageQueryDTO query);


    boolean setDefaultAddress(Long userId, Long addressId);

    Optional<UserAddress> getDefaultAddress(Long userId);

    boolean batchUpdateAddressStatus(List<Long> ids, UserAddressStatusEnum status, Long userId);

    List<UserAddress> getUserAddressList(Long userId);
}

