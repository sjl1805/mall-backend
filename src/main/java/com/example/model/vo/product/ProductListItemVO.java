package com.example.model.vo.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Schema(name = "商品列表项VO")
public class ProductListItemVO {
    @Schema(name = "productId", description = "商品ID", example = "1001")
    private Long productId;

    @Schema(name = "productName", description = "商品名称", example = "智能手机")
    private String productName;

    @Schema(name = "mainImage", description = "主图URL", example = "https://example.com/main.jpg")
    private String mainImage;

    @Schema(name = "price", description = "销售价格", example = "2999.00")
    private BigDecimal price;

    @Schema(name = "sales", description = "销量", example = "500")
    private Integer sales;

    @Schema(name = "stockStatus", description = "库存状态", example = "1", allowableValues = {"0", "1"})
    private Integer stockStatus;

    @Schema(name = "createTime", description = "上架时间", example = "2024-03-20T10:00:00")
    private LocalDateTime createTime;
} 