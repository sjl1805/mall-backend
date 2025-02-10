package com.example.model.vo.order;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
@Schema(name = "订单创建VO")
public class OrderCreateVO {
    @Schema(name = "addressId", description = "地址ID", required = true, example = "1")
    @NotNull(message = "请选择收货地址")
    private Long addressId;

    @Schema(name = "couponId", description = "优惠券ID", example = "1001")
    private Long couponId;

    @Schema(name = "buyerRemark", description = "买家留言", example = "请尽快发货")
    @Size(max = 100, message = "留言最长100个字符")
    private String buyerRemark;

    @Schema(name = "cartItemIds", description = "购物车项ID列表", required = true)
    @NotEmpty(message = "请选择要结算的商品")
    private List<Long> cartItemIds;
} 