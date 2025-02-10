package com.example.model.vo.product;

import lombok.Data;

import java.math.BigDecimal;

/**
 * {@code @user} 31815
 * {@code @date}  2025/2/10
 */
@Data
public class ProductSkuVO {
    private Long skuId;
    private String specValues;
    private BigDecimal price;
    private Integer stock;
    private String mainImage;
}
