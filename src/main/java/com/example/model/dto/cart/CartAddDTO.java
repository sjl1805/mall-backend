package com.example.model.dto.cart;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "购物车添加商品请求参数")
@Data
public class CartAddDTO {
    @Schema(description = "商品ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "3001")
    @NotNull(message = "商品ID不能为空")
    private Long productId;

    @Schema(description = "商品数量", requiredMode = Schema.RequiredMode.REQUIRED, minimum = "1", example = "2")
    @NotNull(message = "商品数量不能为空")
    @Min(value = 1, message = "商品数量至少为1")
    private Integer quantity;
} 