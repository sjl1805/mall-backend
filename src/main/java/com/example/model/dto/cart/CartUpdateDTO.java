package com.example.model.dto.cart;

import lombok.Data;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Data
public class CartUpdateDTO {
    @NotNull(message = "购物车项ID不能为空")
    private Long cartId;

    @NotNull(message = "商品数量不能为空")
    @Min(value = 1, message = "商品数量至少为1")
    private Integer quantity;

    @NotNull(message = "选中状态不能为空")
    private Boolean checked;
} 