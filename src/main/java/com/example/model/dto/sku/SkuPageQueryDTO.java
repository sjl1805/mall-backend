package com.example.model.dto.sku;

import com.example.model.enums.ProductSkuStatusEnum;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class SkuPageQueryDTO {
    private Long productId;
    private String specKeyword;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private ProductSkuStatusEnum status;
} 