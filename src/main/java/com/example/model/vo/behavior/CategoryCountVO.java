package com.example.model.vo.behavior;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "分类统计VO")
public class CategoryCountVO {
    @Schema(name = "categoryId", description = "分类ID", example = "1001")
    private Long categoryId;

    @Schema(name = "categoryName", description = "分类名称", example = "数码产品")
    private String categoryName;

    @Schema(name = "count", description = "相关行为次数", example = "50")
    private Integer count;
} 