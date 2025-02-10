package com.example.model.dto.favorite;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "收藏项详情")
@Data
public class FavoriteItemDTO {
    @Schema(description = "收藏项ID", example = "7001")
    private Long favoriteId;

    @Schema(description = "商品ID", example = "3001")
    private Long productId;

    @Schema(description = "商品名称", example = "智能手机")
    private String productName;

    @Schema(description = "商品主图URL", example = "https://example.com/product.jpg")
    private String mainImage;

    @Schema(description = "商品价格", example = "2999.00")
    private BigDecimal price;

    @Schema(description = "商品库存", example = "100")
    private Integer stock;

    @Schema(description = "收藏时间", example = "2024-03-15 10:00:00")
    private LocalDateTime collectTime;

    @Schema(description = "所属收藏夹名称", example = "我的最爱")
    private String folderName;
} 