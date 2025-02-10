package com.example.model.dto.coupon;

import com.example.model.enums.CouponStatusEnum;
import com.example.model.enums.CouponTypeEnum;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class AdminCouponDTO {
    private Long id;
    private String name;
    private CouponTypeEnum type;
    private BigDecimal value;
    private BigDecimal minAmount;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private CouponStatusEnum status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer totalIssued; // 已发放数量
    private Integer usedCount;   // 已使用数量
    private Long userId;
    private String userName;
    private String couponName;
    private String statusDesc;
    private LocalDateTime getTime;
    private LocalDateTime useTime;
    private Long orderId;
} 