package com.example.model.vo.behavior;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
@Schema(name = "行为概览VO")
public class BehaviorOverviewVO {
    @Schema(name = "totalActions", description = "总行为次数", example = "1000")
    private Integer totalActions;

    @Schema(name = "actionTrend", description = "行为趋势数据", example = "{\"2024-03-20\": 50}")
    private Map<String, Integer> actionTrend;

    @Schema(name = "hotProducts", description = "热门商品TOP10")
    private List<ProductHeatVO> hotProducts;

    @Schema(name = "userProfile", description = "用户行为画像", example = "{\"viewRate\": 0.6, \"buyRate\": 0.1}")
    private Map<String, BigDecimal> userProfile;
} 