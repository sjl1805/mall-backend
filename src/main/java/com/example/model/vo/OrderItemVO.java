package com.example.model.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单商品项视图对象
 */
@Data
public class OrderItemVO {
    /**
     * 订单商品ID
     */
    private Long id;

    /**
     * 订单ID
     */
    private Long orderId;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 商品ID
     */
    private Long productId;

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
     * 商品总价
     */
    private BigDecimal totalPrice;

    /**
     * 创建时间
     */
    private Date createTime;
} 