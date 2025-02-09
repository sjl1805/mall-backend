package com.example.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 商品规格表
 *
 * @TableName product_spec
 */
@TableName(value = "product_spec", autoResultMap = true)
@Data
public class ProductSpec implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 规格ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 商品ID
     */
    private Long productId;
    /**
     * 规格名称
     */
    private String specName;
    /**
     * 规格值，JSON格式
     */
    private String specValues;
    /**
     * 创建时间（带时区）
     */
    private Date createTime;
    /**
     * 更新时间（带时区）
     */
    private Date updateTime;
    /**
     * 乐观锁版本号
     */
    private Integer version;
    /**
     * 删除标志：0存在 1删除
     */
    private Integer deleted;
}