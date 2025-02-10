package com.example.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;


/**
 * 订单商品表
 *
 * @TableName order_item
 */
@TableName(value = "order_item", autoResultMap = true)
@Data
@EqualsAndHashCode(callSuper = true)
public class OrderItem extends BaseEntity {
    /**
     * 订单商品ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 订单ID
     */
    private Long orderId;
    /**
     * 商品ID
     */
    private Long productId;
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 商品主图URL
     */
    private String productImage;
    /**
     * 商品单价
     */
    private BigDecimal price;
    /**
     * 购买数量
     */
    private Integer quantity;
    /**
     * 商品总价
     */
    private BigDecimal totalAmount;

}