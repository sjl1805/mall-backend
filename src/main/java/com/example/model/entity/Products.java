package com.example.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品表
 *
 * @TableName products
 */
@TableName(value = "products", autoResultMap = true)
@Data
public class Products implements Serializable {
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
    private Object images;
    /**
     * 创建时间（带时区）
     */
    private Date createTime;
    /**
     * 更新时间（带时区）
     */
    private Date updateTime;
    /**
     * 商品状态：0-下架 1-上架
     */
    private Integer status;
    /**
     * 乐观锁版本号
     */
    private Integer version;
    /**
     * 删除标志：0存在 1删除
     */
    private Integer deleted;
}