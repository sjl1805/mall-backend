package com.example.model.vo.order;

import lombok.Data;

import java.math.BigDecimal;

/**
 * {@code @user} 31815
 * {@code @date}  2025/2/10
 */
@Data
public class OrderItemVO {
    private String productName;
    private String productImage;
    private BigDecimal price;
    private Integer quantity;
    private BigDecimal totalPrice;
}
