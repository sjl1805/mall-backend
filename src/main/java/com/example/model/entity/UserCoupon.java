package com.example.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户优惠券表
 *
 * @TableName user_coupon
 */
@TableName(value = "user_coupon", autoResultMap = true)
@Data
public class UserCoupon implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 用户优惠券ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 优惠券ID
     */
    private Long couponId;
    /**
     * 用户优惠券状态：0-未使用 1-已使用 2-已过期
     */
    private Integer status;
    /**
     * 使用的订单ID
     */
    private Long orderId;
    /**
     * 领取时间
     */
    private Date getTime;
    /**
     * 使用时间
     */
    private Date useTime;
    /**
     * 乐观锁版本号
     */
    private Integer version;
    /**
     * 删除标志：0存在 1删除
     */
    private Integer deleted;
}