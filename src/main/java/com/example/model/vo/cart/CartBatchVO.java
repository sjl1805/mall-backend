package com.example.model.vo.cart;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.List;

@Data
@Schema(name = "批量操作VO")
public class CartBatchVO {
    @Schema(name = "cartItemIds", description = "购物车项ID列表", required = true)
    @NotEmpty(message = "请选择要操作的商品")
    private List<Long> cartItemIds;

    @Schema(name = "operationType", description = "操作类型", example = "check",
            allowableValues = {"check", "uncheck", "delete"})
    @Pattern(regexp = "check|uncheck|delete", message = "非法操作类型")
    private String operationType;
} 