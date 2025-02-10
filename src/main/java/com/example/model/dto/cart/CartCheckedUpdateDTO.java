package com.example.model.dto.cart;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Schema(description = "购物车选中状态更新请求参数")
@Data
public class CartCheckedUpdateDTO {
    @Schema(description = "需要更新的购物车项ID列表", requiredMode = Schema.RequiredMode.REQUIRED, example = "[1001,1002]")
    @NotEmpty(message = "购物车项不能为空")
    private List<Long> cartIds;

    @Schema(description = "目标选中状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    @NotNull(message = "目标状态不能为空")
    private Boolean targetChecked;
} 