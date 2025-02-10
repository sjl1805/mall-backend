package com.example.model.vo.behavior;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
@Schema(name = "商品热度VO")
public class ProductHeatVO {
    @Schema(name = "productId", description = "商品ID", example = "1001")
    private Long productId;

    @Schema(name = "productName", description = "商品名称", example = "智能手机")
    private String productName;

    @Schema(name = "heatValue", description = "热度值", example = "0.95")
    private BigDecimal heatValue;

    @Schema(name = "actionDistribution", description = "行为类型分布", example = "{\"view\": 50, \"collect\": 10}")
    private Map<String, Integer> actionDistribution;
} 