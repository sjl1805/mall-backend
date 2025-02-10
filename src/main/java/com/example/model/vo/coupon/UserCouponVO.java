package com.example.model.vo.coupon;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Schema(name = "用户优惠券VO")
public class UserCouponVO {
    @Schema(name = "userCouponId", description = "用户优惠券ID", example = "3001")
    private Long userCouponId;

    @Schema(name = "couponName", description = "优惠券名称", example = "春季大促券")
    private String couponName;

    @Schema(name = "couponType", description = "优惠券类型", example = "1", allowableValues = {"1", "2"})
    private Integer couponType;

    @Schema(name = "value", description = "优惠券面值", example = "100.00")
    private BigDecimal value;

    @Schema(name = "minAmount", description = "使用门槛", example = "500.00")
    private BigDecimal minAmount;

    @Schema(name = "startTime", description = "有效期开始时间", example = "2024-03-20T00:00:00")
    private LocalDateTime startTime;

    @Schema(name = "endTime", description = "有效期结束时间", example = "2024-04-20T23:59:59")
    private LocalDateTime endTime;

    @Schema(name = "status", description = "使用状态", example = "0", allowableValues = {"0", "1", "2"})
    private Integer status;

    @Schema(name = "applyScope", description = "适用商品范围", example = "指定分类商品")
    private String applyScope;
} 