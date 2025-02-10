package com.example.model.dto.coupon;

import com.example.model.enums.CouponTypeEnum;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class UserCouponDTO {
    private Long couponId;
    private String name;
    private CouponTypeEnum type;
    private BigDecimal value;
    private BigDecimal minAmount;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String statusDesc;
    private Boolean isExpired;
} 