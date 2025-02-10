package com.example.model.dto.behavior;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class BehaviorAnalysisQueryDTO {
    private Long productId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String behaviorType;
    private String analysisDimension; // 分析维度：hour/day/week/month
} 