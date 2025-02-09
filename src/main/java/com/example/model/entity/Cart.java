package com.example.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 购物车表
 *
 * @TableName cart
 */
@TableName(value = "cart", autoResultMap = true)
@Data
public class Cart implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
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
    private Integer checked;
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