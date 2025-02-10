package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.model.dto.coupon.CouponPageQueryDTO;
import com.example.model.entity.UserCoupon;

import java.util.List;

/**
 * @author 31815
 * @description 针对表【user_coupon(用户优惠券表)】的数据库操作Service
 * @createDate 2025-02-10 02:08:27
 */
public interface UserCouponService extends IService<UserCoupon> {
    CouponPageQueryDTO getUserCouponPage(Long userId, CouponPageQueryDTO query);

    boolean markCouponsAsUsed(List<Long> couponIds, Long userId);

    int getValidCouponCount(Long userId);

    List<UserCoupon> getExpiringCoupons();

    boolean expireCoupons();

    boolean checkCouponValid(Long userCouponId, Long userId);
}

