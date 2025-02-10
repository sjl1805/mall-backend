package com.example.model.vo.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
@Schema(name = "订单项VO")
public class OrderItemVO {
    @Schema(name = "productId", description = "商品ID", example = "1001")
    private Long productId;

    @Schema(name = "skuId", description = "SKU ID", example = "2001")
    private Long skuId;

    @Schema(name = "productName", description = "商品名称", example = "智能手机")
    private String productName;

    @Schema(name = "specs", description = "规格属性", example = "{\"颜色\":\"黑色\",\"内存\":\"128GB\"}")
    private Map<String, String> specs;

    @Schema(name = "price", description = "单价", example = "2999.00")
    private BigDecimal price;

    @Schema(name = "quantity", description = "数量", example = "1")
    private Integer quantity;

    @Schema(name = "mainImage", description = "商品主图", example = "https://example.com/main.jpg")
    private String mainImage;

    @Schema(name = "reviewStatus", description = "评价状态", example = "0")
    private Integer reviewStatus;
} 
