package com.example.model.dto.cart;

import com.example.model.enums.CartCheckedStatusEnum;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class CartItemDTO {
    private Long cartId;
    private Long productId;
    private String productName;
    private String mainImage;
    private BigDecimal price;
    private Integer quantity;
    private BigDecimal totalAmount;
    private CartCheckedStatusEnum checked;
    private Integer stock;
} 