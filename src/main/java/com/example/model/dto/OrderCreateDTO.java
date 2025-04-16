package com.example.model.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 创建订单DTO
 */
@Data
public class OrderCreateDTO {
    /**
     * 收货地址ID
     */
    @NotNull(message = "收货地址不能为空")
    private Long addressId;

    /**
     * 支付方式：1-支付宝，2-微信
     */
    @NotNull(message = "支付方式不能为空")
    private Integer payType;

    /**
     * 订单备注
     */
    private String note;

    /**
     * 运费
     */
    private BigDecimal freightAmount;

    /**
     * 是否从购物车创建订单：true-是，false-否(直接购买)
     */
    private Boolean fromCart = true;

    /**
     * 非购物车下单时的商品ID
     */
    private Long productId;

    /**
     * 非购物车下单时的商品数量
     */
    private Integer quantity;
} 