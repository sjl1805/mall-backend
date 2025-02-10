package com.example.model.dto.category;

import com.example.model.enums.CategoryStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "分类分页查询参数")
@Data
public class CategoryPageQueryDTO {
    @Schema(description = "分类名称（模糊查询）", example = "电子")
    private String name;

    @Schema(description = "分类状态", implementation = CategoryStatusEnum.class)
    private CategoryStatusEnum status;

    @Schema(description = "分类层级", example = "1", allowableValues = {"1", "2", "3"})
    private Integer level;
} 