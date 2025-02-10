package com.example.model.dto.product;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class AdminProductDTO {
    private Long id;
    private String name;
    private String categoryName;
    private BigDecimal price;
    private Integer stock;
    private String statusDesc;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer salesVolume;
} 