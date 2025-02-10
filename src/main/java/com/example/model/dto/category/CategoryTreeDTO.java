package com.example.model.dto.category;

import com.example.model.enums.CategoryStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class CategoryTreeDTO extends CategoryDTO {
    private List<CategoryTreeDTO> children;
}

@Data
class CategoryDTO {
    private Long id;
    private Long parentId;
    private String name;
    private String icon;
    private Integer level;
    private Integer sort;
    private CategoryStatusEnum status;
} 