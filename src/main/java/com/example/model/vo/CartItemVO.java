package com.example.model.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 购物车项视图对象
 */
@Data
public class CartItemVO {
    /**
     * 购物车ID
     */
    private Long id;

    /**
     * 商品ID
     */
    private Long productId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品图片
     */
    private String productImage;

    /**
     * 商品价格
     */
    private BigDecimal price;

    /**
     * 购买数量
     */
    private Integer quantity;

    /**
     * 是否选中：0-未选中，1-选中
     */
    private Integer checked;

    /**
     * 商品总价
     */
    private BigDecimal totalPrice;

    /**
     * 商品库存
     */
    private Integer stock;

    /**
     * 商品状态：0-下架，1-上架
     */
    private Integer status;
} 