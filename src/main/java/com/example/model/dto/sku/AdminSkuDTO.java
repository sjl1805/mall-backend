package com.example.model.dto.sku;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class AdminSkuDTO {
    private Long id;
    private Long productId;
    private String productName;
    private String specValues;
    private BigDecimal price;
    private Integer stock;
    private Integer sales;
    private String mainImage;
    private String statusDesc;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
} 