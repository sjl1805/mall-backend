package com.example.model.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Schema(description = "订单项详情")
@Data
public class OrderItemDTO {
    @Schema(description = "订单项ID", example = "5001")
    private Long orderItemId;

    @Schema(description = "商品ID", example = "3001")
    private Long productId;

    @Schema(description = "商品名称", example = "智能手机")
    private String productName;

    @Schema(description = "商品图片", example = "https://example.com/product.jpg")
    private String productImage;

    @Schema(description = "商品单价", example = "1999.99")
    private BigDecimal price;

    @Schema(description = "购买数量", example = "2")
    private Integer quantity;

    @Schema(description = "总金额", example = "3999.98")
    private BigDecimal totalAmount;

    @Schema(description = "商品规格", example = "颜色:黑色; 内存:256GB")
    private String skuSpec;
} 