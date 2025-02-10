package com.example.model.dto.cart;

import lombok.Data;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "购物车项更新请求参数")
@Data
public class CartUpdateDTO {
    @Schema(description = "购物车项ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "5001")
    @NotNull(message = "购物车项ID不能为空")
    private Long cartId;

    @Schema(description = "更新后的商品数量", requiredMode = Schema.RequiredMode.REQUIRED, minimum = "1", example = "3")
    @NotNull(message = "商品数量不能为空")
    @Min(value = 1, message = "商品数量至少为1")
    private Integer quantity;

    @Schema(description = "更新后的选中状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    @NotNull(message = "选中状态不能为空")
    private Boolean checked;
} 