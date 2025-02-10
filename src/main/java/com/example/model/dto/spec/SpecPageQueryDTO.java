package com.example.model.dto.spec;

import com.example.model.dto.PaginationDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Schema(description = "规格分页查询参数")
@Data
@EqualsAndHashCode(callSuper = true)
public class SpecPageQueryDTO extends PaginationDTO<AdminSpecDTO> {
    @Schema(description = "商品ID", example = "3001")
    private Long productId;
    @Schema(description = "规格名称（模糊查询）", example = "颜色")
    private String specName;
} 