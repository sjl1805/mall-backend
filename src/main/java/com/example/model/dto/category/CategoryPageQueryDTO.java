package com.example.model.dto.category;

import lombok.Data;
import com.example.model.enums.CategoryStatusEnum;

@Data
public class CategoryPageQueryDTO {
    private String name;
    private CategoryStatusEnum status;
    private Integer level;
} 