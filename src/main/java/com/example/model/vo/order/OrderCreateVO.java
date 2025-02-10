package com.example.model.vo.order;

import com.example.model.enums.OrderStatusEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * {@code @user} 31815
 * {@code @date}  2025/2/10
 */
@Data
public class OrderCreateVO {
    private Long orderId;
    private String orderNo;
    private BigDecimal totalAmount;
    private LocalDateTime createTime;
    private OrderStatusEnum status;
}
