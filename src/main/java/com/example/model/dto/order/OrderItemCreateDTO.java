package com.example.model.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Schema(description = "订单项创建参数")
@Data
public class OrderItemCreateDTO {
    @Schema(description = "商品ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "3001")
    @NotNull(message = "商品ID不能为空")
    private Long productId;

    @Schema(description = "商品数量", requiredMode = Schema.RequiredMode.REQUIRED, minimum = "1", example = "2")
    @NotNull(message = "商品数量不能为空")
    @Min(value = 1, message = "商品数量至少为1")
    private Integer quantity;

    @Schema(description = "商品单价", requiredMode = Schema.RequiredMode.REQUIRED, minimum = "0.01", example = "1999.99")
    @NotNull(message = "商品单价不能为空")
    @DecimalMin(value = "0.01", message = "单价不能小于0.01")
    private BigDecimal price;
} 