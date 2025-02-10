package com.example.model.dto.category;

import com.example.model.enums.CategoryStatusEnum;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AdminCategoryDTO {
    private Long id;
    private Long parentId;
    private String parentName;
    private String name;
    private String icon;
    private Integer level;
    private Integer sort;
    private CategoryStatusEnum status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
} 