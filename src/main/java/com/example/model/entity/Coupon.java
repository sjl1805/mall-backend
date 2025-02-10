package com.example.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.model.enums.CouponStatusEnum;
import com.example.model.enums.CouponTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 优惠券表
 *
 * @TableName coupon
 */
@TableName(value = "coupon", autoResultMap = true)
@Data
@EqualsAndHashCode(callSuper = true)
public class Coupon extends BaseEntity {
    /**
     * 优惠券ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 优惠券名称
     */
    private String name;
    /**
     * 优惠券类型：1-满减券 2-折扣券
     */
    private CouponTypeEnum type;
    /**
     * 优惠券面值
     */
    private BigDecimal value;
    /**
     * 使用门槛
     */
    private BigDecimal minAmount;
    /**
     * 生效时间
     */
    private LocalDateTime startTime;
    /**
     * 失效时间
     */
    private LocalDateTime endTime;
    /**
     * 状态：0-失效 1-生效
     */
    private CouponStatusEnum status;
}