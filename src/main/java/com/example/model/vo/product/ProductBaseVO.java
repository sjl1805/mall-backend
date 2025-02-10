package com.example.model.vo.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(name = "商品基础信息VO")
public class ProductBaseVO {
    @Schema(name = "productId", description = "商品ID", example = "1001")
    private Long productId;

    @Schema(name = "productName", description = "商品名称", example = "智能手机")
    private String productName;

    @Schema(name = "mainImage", description = "主图URL", example = "https://example.com/image.jpg")
    private String mainImage;

    @Schema(name = "price", description = "商品价格", example = "2999.00")
    private BigDecimal price;
} 