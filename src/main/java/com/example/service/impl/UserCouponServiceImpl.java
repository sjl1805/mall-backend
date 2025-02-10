package com.example.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mapper.UserCouponMapper;
import com.example.model.dto.coupon.CouponPageQueryDTO;
import com.example.model.entity.UserCoupon;
import com.example.service.UserCouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 31815
 * @description 针对表【user_coupon(用户优惠券表)】的数据库操作Service实现
 * @createDate 2025-02-10 02:08:27
 */
@Service
@RequiredArgsConstructor
public class UserCouponServiceImpl extends ServiceImpl<UserCouponMapper, UserCoupon>
        implements UserCouponService {

    private final UserCouponMapper userCouponMapper;

    @Override
    public IPage<CouponPageQueryDTO> getUserCouponPage(Long userId, CouponPageQueryDTO query) {
        return userCouponMapper.selectUserCoupons(query, userId);
    }

    @Override
    @Transactional
    public boolean markCouponsAsUsed(List<Long> couponIds, Long userId) {
        return userCouponMapper.batchMarkAsUsed(couponIds, userId) > 0;
    }

    @Override
    public int getValidCouponCount(Long userId) {
        return userCouponMapper.countUnusedCoupons(userId);
    }

    @Override
    public List<UserCoupon> getExpiringCoupons() {
        return userCouponMapper.selectExpiringUserCoupons();
    }

    @Override
    @Transactional
    public boolean expireCoupons() {
        return userCouponMapper.batchExpireUserCoupons() > 0;
    }

    @Override
    public boolean checkCouponValid(Long userCouponId, Long userId) {
        return userCouponMapper.checkCouponAvailability(userCouponId, userId) > 0;
    }
}




