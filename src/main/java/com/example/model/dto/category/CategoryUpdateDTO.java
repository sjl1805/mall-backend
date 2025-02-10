package com.example.model.dto.category;

import com.example.model.enums.CategoryStatusEnum;
import lombok.Data;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;

@Data
public class CategoryUpdateDTO {
    @Size(max = 64, message = "分类名称最长64个字符")
    private String name;

    @Size(max = 255, message = "图标URL过长")
    private String icon;

    @Min(value = 0, message = "排序值不能小于0")
    private Integer sort;

    private CategoryStatusEnum status;
} 