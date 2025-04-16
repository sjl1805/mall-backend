package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.model.entity.Address;

import java.util.List;

/**
 * @author 28619
 * @description 针对表【address(收货地址表)】的数据库操作Service
 * @createDate 2025-04-09 17:42:15
 */
public interface AddressService extends IService<Address> {
    /**
     * 获取用户的地址列表
     *
     * @param userId 用户ID
     * @return 地址列表
     */
    List<Address> getUserAddresses(Long userId);

    /**
     * 获取用户的默认地址
     *
     * @param userId 用户ID
     * @return 默认地址，如果没有默认地址则返回null
     */
    Address getDefaultAddress(Long userId);

    /**
     * 添加地址
     *
     * @param address 地址信息
     * @param userId  用户ID
     * @return 添加后的地址
     */
    Address addAddress(Address address, Long userId);

    /**
     * 更新地址
     *
     * @param address 地址信息
     * @param userId  用户ID
     * @return 是否成功
     */
    boolean updateAddress(Address address, Long userId);

    /**
     * 删除地址
     *
     * @param addressId 地址ID
     * @param userId    用户ID
     * @return 是否成功
     */
    boolean deleteAddress(Long addressId, Long userId);

    /**
     * 设置默认地址
     *
     * @param addressId 地址ID
     * @param userId    用户ID
     * @return 是否成功
     */
    boolean setDefaultAddress(Long addressId, Long userId);
}
