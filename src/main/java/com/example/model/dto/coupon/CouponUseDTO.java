package com.example.model.dto.coupon;

import lombok.Data;
import jakarta.validation.constraints.NotNull;

@Data
public class CouponUseDTO {
    @NotNull(message = "用户优惠券ID不能为空")
    private Long userCouponId;

    @NotNull(message = "订单ID不能为空")
    private Long orderId;
} 