package com.example.model.vo.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "分类信息VO")
public class CategoryInfoVO {
    @Schema(name = "分类ID", example = "1001")
    private Long id;

    @Schema(name = "分类名称", example = "手机数码")
    private String name;

    @Schema(name = "分类级别", example = "1")
    private Integer level;
} 