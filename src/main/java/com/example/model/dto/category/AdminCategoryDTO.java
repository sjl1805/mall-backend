package com.example.model.dto.category;

import com.example.model.enums.CategoryStatusEnum;
import lombok.Data;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "管理员视角分类详情")
@Data
public class AdminCategoryDTO {
    @Schema(description = "分类ID", example = "1001")
    private Long id;
    
    @Schema(description = "父分类ID", example = "0")
    private Long parentId;
    
    @Schema(description = "父分类名称", example = "根分类")
    private String parentName;
    
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
    
    @Schema(description = "创建时间", example = "2024-01-01 12:00:00")
    private LocalDateTime createTime;
    
    @Schema(description = "更新时间", example = "2024-01-01 12:00:00")
    private LocalDateTime updateTime;
} 