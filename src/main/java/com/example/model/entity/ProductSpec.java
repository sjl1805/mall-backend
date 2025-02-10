package com.example.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 商品规格表
 *
 * @TableName product_spec
 */
@TableName(value = "product_spec", autoResultMap = true)
@Data
@EqualsAndHashCode(callSuper = true)
public class ProductSpec extends BaseEntity {
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

}