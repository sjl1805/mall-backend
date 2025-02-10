package com.example.model.dto.coupon;

import com.example.model.enums.UserCouponStatusEnum;
import lombok.Data;

@Data
public class CouponPageQueryDTO {
    private String couponName;
    private UserCouponStatusEnum status;
    private Boolean expired;
} 