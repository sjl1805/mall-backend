package com.example.model.vo.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(name = "优惠信息VO")
public class CouponInfoVO {
    @Schema(name = "couponName", description = "优惠券名称", example = "春季大促券")
    private String couponName;

    @Schema(name = "discountAmount", description = "优惠金额", example = "100.00")
    private BigDecimal discountAmount;
} 