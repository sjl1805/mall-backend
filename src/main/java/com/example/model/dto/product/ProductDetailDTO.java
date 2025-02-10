package com.example.model.dto.product;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductDetailDTO {
    private Long id;
    private String name;
    private String categoryName;
    private String description;
    private BigDecimal price;
    private Integer stock;
    private List<String> images;
    private String statusDesc;
    private Double averageRating;
    private Integer reviewCount;
} 