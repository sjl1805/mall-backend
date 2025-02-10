package com.example.model.dto.product;

import com.example.model.enums.ProductStatusEnum;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductPageQueryDTO {
    private String name;
    private Long categoryId;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private ProductStatusEnum status;
    private Boolean hasStock;
} 