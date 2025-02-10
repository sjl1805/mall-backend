package com.example.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.model.enums.OrderCommentStatusEnum;
import com.example.model.enums.OrderPaymentMethodEnum;
import com.example.model.enums.OrderStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单表
 *
 * @TableName orders
 */
@TableName(value = "orders", autoResultMap = true)
@Data
@EqualsAndHashCode(callSuper = true)
public class Orders extends BaseEntity {
    /**
     * 订单ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 订单号
     */
    private String orderNo;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 订单总金额
     */
    private BigDecimal totalAmount;
    /**
     * 实付金额
     */
    private BigDecimal payAmount;
    /**
     * 订单状态：0-待支付 1-已支付 2-已发货 3-已完成 4-已取消
     */
    private OrderStatusEnum status;
    /**
     * 收货人姓名
     */
    private String receiverName;
    /**
     * 收货人电话
     */
    private String receiverPhone;
    /**
     * 收货地址
     */
    private String receiverAddress;
    /**
     * 支付时间
     */
    private LocalDateTime paymentTime;
    /**
     * 发货时间
     */
    private LocalDateTime deliveryTime;
    /**
     * 收货时间
     */
    private LocalDateTime receiveTime;
    /**
     * 支付方式：1-支付宝 2-微信 3-银联
     */
    private OrderPaymentMethodEnum paymentMethod;
    /**
     * 物流公司
     */
    private String logisticsCompany;
    /**
     * 运单号
     */
    private String trackingNumber;
    /**
     * 评价状态：0未评价 1已评价
     */
    private OrderCommentStatusEnum commentStatus;
    /**
     * 时区信息
     */
    private String timezone;
}