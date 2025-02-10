package com.example.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.model.enums.CartCheckedStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 购物车表
 *
 * @TableName cart
 */
@TableName(value = "cart", autoResultMap = true)
@Data
@EqualsAndHashCode(callSuper = true)
public class Cart extends BaseEntity {
    /**
     * 购物车ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 用户ID
     */
    @TableField("user_id")
    private Long userId;
    /**
     * 商品ID
     */
    private Long productId;
    /**
     * 商品数量
     */
    private Integer quantity;
    /**
     * 选中状态：0-未选中 1-已选中
     */
    private CartCheckedStatusEnum checked;
}