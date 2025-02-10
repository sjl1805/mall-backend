package com.example.model.dto.cart;

import lombok.Data;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@Data
public class CartCheckedUpdateDTO {
    @NotEmpty(message = "购物车项不能为空")
    private List<Long> cartIds;

    @NotNull(message = "目标状态不能为空")
    private Boolean targetChecked;
} 