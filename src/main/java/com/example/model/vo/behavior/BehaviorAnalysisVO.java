package com.example.model.vo.behavior;

import com.example.model.vo.product.ProductBaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Schema(name = "行为分析VO")
public class BehaviorAnalysisVO {
    @Schema(name = "recentViews", description = "最近浏览商品")
    private List<ProductBaseVO> recentViews;

    @Schema(name = "collectTrend", description = "收藏趋势数据", example = "{\"2024-03\": 5}")
    private Map<String, Integer> collectTrend;

    @Schema(name = "hotCategories", description = "热门收藏分类")
    private List<CategoryCountVO> hotCategories;
} 