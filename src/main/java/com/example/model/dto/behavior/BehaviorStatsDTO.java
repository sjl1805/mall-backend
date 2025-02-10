package com.example.model.dto.behavior;

import lombok.Data;

@Data
public class BehaviorStatsDTO {
    private String timeSegment;
    private Integer viewCount;
    private Integer collectCount;
    private Integer purchaseCount;
    private Double avgDuration;
} 