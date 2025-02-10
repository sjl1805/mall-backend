package com.example.model.dto.coupon;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "用户优惠券信息")
@Data
public class UserCouponDTO {
    @Schema(description = "用户优惠券ID", example = "3001")
    private Long userCouponId;
    @Schema(description = "优惠券名称", example = "新用户专享券")
    private String couponName;
    @Schema(description = "优惠描述", example = "满100减50")
    private String discountDesc;
    @Schema(description = "状态描述", example = "未使用")
    private String statusDesc;
    @Schema(description = "领取时间", example = "2024-03-15 10:00:00")
    private LocalDateTime getTime;
    @Schema(description = "过期时间", example = "2024-04-15 23:59:59")
    private LocalDateTime expireTime;
} 