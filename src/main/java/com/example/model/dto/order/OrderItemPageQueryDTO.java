package com.example.model.dto.order;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class OrderItemPageQueryDTO {
    private String orderNo;
    private String productName;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
} 