package com.example.model.dto.sku;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class SkuDetailDTO {
    private Long skuId;
    private String productName;
    private String specValues;
    private BigDecimal price;
    private Integer stock;
    private Integer sales;
    private String mainImage;
    private String statusDesc;
} 