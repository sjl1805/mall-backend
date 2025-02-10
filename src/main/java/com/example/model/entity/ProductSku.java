package com.example.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 商品SKU表
 *
 * @TableName product_sku
 */
@TableName(value = "product_sku", autoResultMap = true)
@Data
@EqualsAndHashCode(callSuper = true)
public class ProductSku extends BaseEntity {
    /**
     * SKU ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 商品ID
     */
    private Long productId;
    /**
     * 规格值组合（JSON）
     */
    private String specValues;
    /**
     * 价格
     */
    private BigDecimal price;
    /**
     * 库存
     */
    private Integer stock;
    /**
     * 销量
     */
    private Integer sales;
    /**
     * SKU主图
     */
    private String mainImage;
    /**
     * SKU状态：0-下架 1-上架
     */
    private Integer status;
}
