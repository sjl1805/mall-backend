package com.example.model.dto.favorite;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class FavoriteItemDTO {
    private Long favoriteId;
    private Long productId;
    private String productName;
    private String mainImage;
    private BigDecimal price;
    private Integer stock;
    private LocalDateTime collectTime;
    private String folderName;
} 