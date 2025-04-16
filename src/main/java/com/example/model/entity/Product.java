package com.example.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品表
 *
 * @TableName product
 */
@TableName(value = "product")
@Data
public class Product implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
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
     * 商品价格
     */
    private BigDecimal price;
    /**
     * 原价
     */
    private BigDecimal originalPrice;
    /**
     * 库存
     */
    private Integer stock;
    /**
     * 销量
     */
    private Integer sales;
    /**
     * 商品主图
     */
    private String image;
    /**
     * 商品图片集
     */
    private String images;
    /**
     * 商品描述
     */
    private String description;
    /**
     * 商品详情
     */
    private String detail;
    /**
     * 状态：0-下架，1-上架
     */
    private Integer status;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

}