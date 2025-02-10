package com.example.model.vo.product;

import com.example.model.enums.ProductStatusEnum;

import java.math.BigDecimal;

/**
 * {@code @user} 31815
 * {@code @date}  2025/2/10
 */
public class ProductSimpleVO {
    private Long id;
    private String name;
    private BigDecimal price;
    private String mainImage;
    private Integer sales;
    private ProductStatusEnum status;
}
