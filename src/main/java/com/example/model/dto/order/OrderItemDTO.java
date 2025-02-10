package com.example.model.dto.order;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class OrderItemDTO {
    private Long orderItemId;
    private Long productId;
    private String productName;
    private String productImage;
    private BigDecimal price;
    private Integer quantity;
    private BigDecimal totalAmount;
    private String skuSpec; // 商品规格信息
} 