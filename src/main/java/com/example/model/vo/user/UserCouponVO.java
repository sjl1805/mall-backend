package com.example.model.vo.user;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * {@code @user} 31815
 * {@code @date}  2025/2/10
 */
@Data
public class UserCouponVO {
    private String couponName;
    private String discountDesc;
    private LocalDateTime expireTime;
    private String statusDesc;
    private BigDecimal minAmount;
    private BigDecimal discountValue;
}
