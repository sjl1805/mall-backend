package com.example.model.dto.coupon;

import lombok.Data;
import jakarta.validation.constraints.NotNull;

@Data
public class CouponReceiveDTO {
    @NotNull(message = "优惠券ID不能为空")
    private Long couponId;
} 