package com.example.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单表
 *
 * @TableName order
 */
@TableName(value = "orders")
@Data
public class Order implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 订单ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 订单编号
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
     * 运费
     */
    private BigDecimal freightAmount;
    /**
     * 支付方式：1-支付宝，2-微信
     */
    private Integer payType;
    /**
     * 订单状态：0-待付款，1-待发货，2-待收货，3-已完成，4-已取消
     */
    private Integer status;
    /**
     * 收货地址ID
     */
    private Long addressId;
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
     * 订单备注
     */
    private String note;
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