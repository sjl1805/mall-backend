package com.example.model.vo.coupon;

import com.example.model.vo.user.UserCouponVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * {@code @user} 31815
 * {@code @date}  2025/2/10
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AvailableCouponVO extends UserCouponVO {
    private Boolean isApplicable; // 是否适用于当前订单
    private BigDecimal discountAmount; // 预计折扣金额
}