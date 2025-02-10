package com.example.model.dto.coupon;


import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserCouponDTO {
    private Long userCouponId;
    private String couponName;
    private String discountDesc;
    private String statusDesc;
    private LocalDateTime getTime;
    private LocalDateTime expireTime;
} 