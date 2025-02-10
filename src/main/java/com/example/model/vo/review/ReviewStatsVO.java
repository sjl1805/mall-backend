package com.example.model.vo.review;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
@Schema(name = "评价统计VO")
public class ReviewStatsVO {
    @Schema(name = "totalReviews", description = "总评价数", example = "100")
    private Integer totalReviews;

    @Schema(name = "averageRating", description = "平均评分", example = "4.5")
    private BigDecimal averageRating;

    @Schema(name = "ratingDistribution", description = "评分分布", example = "{\"5\":60, \"4\":30, \"3\":5, \"2\":3, \"1\":2}")
    private Map<Integer, Integer> ratingDistribution;

    @Schema(name = "imageRate", description = "带图评价比例", example = "0.35")
    private BigDecimal imageRate;
} 