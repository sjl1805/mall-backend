package com.example.model.dto.category;

import com.example.model.enums.CategoryStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Schema(description = "分类更新请求参数")
@Data
public class CategoryUpdateDTO {
    @Schema(description = "分类名称", maxLength = 64, example = "智能设备")
    @Size(max = 64, message = "分类名称最长64个字符")
    private String name;

    @Schema(description = "图标URL", maxLength = 255, example = "https://example.com/new-icon.png")
    @Size(max = 255, message = "图标URL过长")
    private String icon;

    @Schema(description = "排序值", minimum = "0", example = "20")
    @Min(value = 0, message = "排序值不能小于0")
    private Integer sort;

    @Schema(description = "分类状态", implementation = CategoryStatusEnum.class)
    private CategoryStatusEnum status;
} 