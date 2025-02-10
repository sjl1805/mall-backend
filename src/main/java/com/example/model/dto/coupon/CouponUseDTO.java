package com.example.model.dto.coupon;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "优惠券使用请求参数")
@Data
public class CouponUseDTO {
    @Schema(description = "用户优惠券ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "3001")
    @NotNull(message = "用户优惠券ID不能为空")
    private Long userCouponId;

    @Schema(description = "订单ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "6001")
    @NotNull(message = "订单ID不能为空")
    private Long orderId;
} 