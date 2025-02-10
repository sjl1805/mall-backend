package com.example.model.vo.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(name = "分类树节点VO")
public class CategoryTreeVO {
    @Schema(name = "分类ID", example = "1001")
    private Long id;

    @Schema(name = "分类名称", example = "手机数码")
    private String name;

    @Schema(name = "子分类列表")
    private List<CategoryTreeVO> children;

    @Schema(name = "分类图标", example = "https://example.com/icon.png")
    private String icon;

    @Schema(name = "分类级别", example = "1", allowableValues = {"1", "2", "3"})
    private Integer level;
} 