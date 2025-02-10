package com.example.model.dto.coupon;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "优惠券领取请求参数")
@Data
public class CouponReceiveDTO {
    @Schema(description = "优惠券ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "2001")
    @NotNull(message = "优惠券ID不能为空")
    private Long couponId;
} 