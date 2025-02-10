package com.example.model.dto.coupon;

import com.example.model.enums.CouponTypeEnum;
import lombok.Data;
import java.math.BigDecimal;
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