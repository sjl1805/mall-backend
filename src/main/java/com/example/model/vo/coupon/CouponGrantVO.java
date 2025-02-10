package com.example.model.vo.coupon;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
@Schema(name = "优惠券发放VO")
public class CouponGrantVO {
    @Schema(name = "couponId", description = "优惠券ID", required = true, example = "1001")
    @NotNull(message = "优惠券ID不能为空")
    private Long couponId;

    @Schema(name = "userIds", description = "用户ID列表", example = "[101, 102]")
    private List<Long> userIds;

    @Schema(name = "quantity", description = "发放数量", example = "1")
    @Min(1)
    @Max(100)
    private Integer quantity = 1;
} 