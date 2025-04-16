package com.example.model.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 订单视图对象
 */
@Data
public class OrderVO {
    /**
     * 订单ID
     */
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
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 订单商品列表
     */
    private List<OrderItemVO> orderItems;

    /**
     * 订单状态的文字描述
     */
    private String statusDesc;

    /**
     * 支付方式的文字描述
     */
    private String payTypeDesc;

    /**
     * 创建时间的格式化字符串
     */
    private String createTimeStr;

    /**
     * 可执行的操作列表
     */
    private List<String> allowActions;
} 