package com.example.model.vo.cart;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(name = "添加购物车VO")
public class CartAddVO {
    @Schema(name = "productId", description = "商品ID", required = true)
    @NotNull(message = "商品ID不能为空")
    private Long productId;

    @Schema(name = "skuId", description = "SKU ID", required = true)
    @NotNull(message = "SKU ID不能为空")
    private Long skuId;

    @Schema(name = "quantity", description = "商品数量", example = "1")
    @Min(value = 1, message = "数量至少1件")
    @Max(value = 99, message = "单商品最多99件")
    private Integer quantity = 1;
} 