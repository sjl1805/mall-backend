package com.example.model.dto.spec;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "规格分页查询参数")
@Data
public class SpecPageQueryDTO {
    @Schema(description = "商品ID", example = "3001")
    private Long productId;
    @Schema(description = "规格名称（模糊查询）", example = "颜色")
    private String specName;
} 