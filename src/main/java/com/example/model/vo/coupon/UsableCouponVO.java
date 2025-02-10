package com.example.model.vo.coupon;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(name = "可用优惠券VO")
public class UsableCouponVO {
    @Schema(name = "userCouponId", description = "用户优惠券ID", example = "3001")
    private Long userCouponId;

    @Schema(name = "couponName", description = "优惠券名称", example = "春季大促券")
    private String couponName;

    @Schema(name = "couponType", description = "优惠券类型", example = "1")
    private Integer couponType;

    @Schema(name = "discountAmount", description = "优惠金额", example = "100.00")
    private BigDecimal discountAmount;

    @Schema(name = "conditionDesc", description = "使用条件提示", example = "满500元可用")
    private String conditionDesc;
} 