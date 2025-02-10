package com.example.model.vo.order;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * {@code @user} 31815
 * {@code @date}  2025/2/10
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class OrderDetailVO extends OrderCreateVO {
    private List<OrderItemVO> items;
    private String receiverInfo;
    private String paymentMethod;
    private String logisticsInfo;
}