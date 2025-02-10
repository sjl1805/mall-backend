package com.example.model.dto.order;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class AdminOrderItemDTO {
    private Long id;
    private String orderNo;
    private Long userId;
    private String productName;
    private BigDecimal price;
    private Integer quantity;
    private BigDecimal totalAmount;
    private LocalDateTime createTime;
} 