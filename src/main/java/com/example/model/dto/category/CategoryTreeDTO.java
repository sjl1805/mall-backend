package com.example.model.dto.category;

import com.example.model.enums.CategoryStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.List;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "分类树形结构响应数据")
@Data
@EqualsAndHashCode(callSuper = true)
public class CategoryTreeDTO extends CategoryDTO {
    @Schema(description = "子分类列表")
    private List<CategoryTreeDTO> children;
}

@Schema(description = "基础分类信息")
@Data
class CategoryDTO {
    @Schema(description = "分类ID", example = "1001")
    private Long id;
    
    @Schema(description = "父分类ID", example = "0")
    private Long parentId;
    
    @Schema(description = "分类名称", example = "电子产品")
    private String name;
    
    @Schema(description = "图标URL", example = "https://example.com/icon.png")
    private String icon;
    
    @Schema(description = "分类层级", example = "1")
    private Integer level;
    
    @Schema(description = "排序值", example = "10")
    private Integer sort;
    
    @Schema(description = "分类状态", implementation = CategoryStatusEnum.class)
    private CategoryStatusEnum status;
} 