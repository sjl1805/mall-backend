package com.example.model.dto.order;

import lombok.Data;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import jakarta.validation.constraints.DecimalMin;
@Data
public class OrderItemCreateDTO {
    @NotNull(message = "商品ID不能为空")
    private Long productId;

    @NotNull(message = "商品数量不能为空")
    @Min(value = 1, message = "商品数量至少为1")
    private Integer quantity;

    @NotNull(message = "商品单价不能为空")
    @DecimalMin(value = "0.01", message = "单价不能小于0.01")
    private BigDecimal price;
} 