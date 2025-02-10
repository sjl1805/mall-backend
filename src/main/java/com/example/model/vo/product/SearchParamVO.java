package com.example.model.vo.product;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(name = "搜索参数VO")
public class SearchParamVO {
    @Schema(name = "搜索关键词", example = "智能手机")
    @Size(max = 50, message = "搜索词最长50个字符")
    private String keyword;

    @Schema(name = "分类ID", example = "1001")
    private Long categoryId;

    @Schema(name = "排序方式", example = "price_desc")
    private String sort;
} 