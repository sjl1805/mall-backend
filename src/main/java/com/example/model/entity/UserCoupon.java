package com.example.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import com.example.model.enums.UserCouponStatusEnum;
/**
 * 用户优惠券表
 *
 * @TableName user_coupon
 */
@TableName(value = "user_coupon", autoResultMap = true)
@Data
@EqualsAndHashCode(callSuper = true)
public class UserCoupon extends BaseEntity {
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
    private UserCouponStatusEnum status;
    /**
     * 使用的订单ID
     */
    private Long orderId;
    /**
     * 领取时间
     */
    private LocalDateTime getTime;
    /**
     * 使用时间
     */
    private LocalDateTime useTime;

}