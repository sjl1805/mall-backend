package com.example.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.example.model.enums.ProductStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

/**
 * 商品表
 *
 * @TableName products
 */
@TableName(value = "products", autoResultMap = true)
@Data
@EqualsAndHashCode(callSuper = true)
public class Products extends BaseEntity {
    /**
     * 商品ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 分类ID
     */
    private Long categoryId;
    /**
     * 商品名称
     */
    private String name;
    /**
     * 商品描述
     */
    private String description;
    /**
     * 价格
     */
    private BigDecimal price;
    /**
     * 库存
     */
    private Integer stock;
    /**
     * 商品图片JSON数组
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> images;
    /**
     * 商品状态：0-下架 1-上架
     */
    private ProductStatusEnum status;
}