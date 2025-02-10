package com.example.model.dto.order;

import com.example.model.enums.OrderStatusEnum;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class AdminOrderDTO {
    private Long id;
    private String orderNo;
    private Long userId;
    private BigDecimal totalAmount;
    private BigDecimal payAmount;
    private OrderStatusEnum status;
    private String paymentMethod;
    private LocalDateTime createTime;
    private LocalDateTime paymentTime;
    private LocalDateTime deliveryTime;
    private String logisticsInfo;
} 