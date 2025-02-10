package com.example.model.vo.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(name = "搜索结果VO")
public class SearchResultVO {
    @Schema(name = "products", description = "商品列表")
    private List<ProductListItemVO> products;

    @Schema(name = "suggestions", description = "搜索建议")
    private List<String> suggestions;

    @Schema(name = "hotKeywords", description = "热门搜索词")
    private List<String> hotKeywords;
} 