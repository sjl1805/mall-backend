package com.example.model.dto.category;

import com.example.model.enums.CategoryStatusEnum;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;

@Data
public class CategoryCreateDTO {
    @NotNull(message = "父分类ID不能为空")
    private Long parentId;

    @NotBlank(message = "分类名称不能为空")
    @Size(max = 64, message = "分类名称最长64个字符")
    private String name;

    @Size(max = 255, message = "图标URL过长")
    private String icon;

    @NotNull(message = "层级不能为空")
    @Min(value = 1, message = "层级最小为1")
    @Max(value = 3, message = "层级最大为3")
    private Integer level;

    @Min(value = 0, message = "排序值不能小于0")
    private Integer sort = 0;

    @NotNull(message = "状态不能为空")
    private CategoryStatusEnum status;
} 