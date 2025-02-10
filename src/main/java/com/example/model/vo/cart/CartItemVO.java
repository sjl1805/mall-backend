package com.example.model.vo.cart;

import com.example.model.vo.product.ProductSimpleVO;
import lombok.Data;

import java.math.BigDecimal;

/**
 * {@code @user} 31815
 * {@code @date}  2025/2/10
 */
@Data
public class CartItemVO {
    private Long cartId;
    private ProductSimpleVO product;
    private Integer quantity;
    private Boolean checked;
    private BigDecimal totalPrice;
    private Boolean inStock;
}
