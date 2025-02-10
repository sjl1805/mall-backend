package com.example.model.dto.behavior;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "用户行为统计结果")
@Data
public class BehaviorStatsDTO {
    @Schema(description = "时间区间", example = "2024-03-15")
    private String timeSegment;

    @Schema(description = "浏览次数", example = "150")
    private Integer viewCount;

    @Schema(description = "收藏次数", example = "30")
    private Integer collectCount;

    @Schema(description = "购买次数", example = "15")
    private Integer purchaseCount;

    @Schema(description = "平均停留时长(秒)", example = "85.5")
    private Double avgDuration;
} 