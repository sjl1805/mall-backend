package com.example.model.dto.behavior;

import lombok.Data;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import com.example.model.enums.UserBehaviorTypeEnum;
@Schema(description = "用户行为分析查询参数")
@Data
public class BehaviorAnalysisQueryDTO {
    @Schema(description = "商品ID", example = "3001")
    private Long productId;
    
    @Schema(description = "开始时间", example = "2024-03-01 00:00:00")
    private LocalDateTime startTime;
    
    @Schema(description = "结束时间", example = "2024-03-31 23:59:59")
    private LocalDateTime endTime;
    
    @Schema(description = "行为类型", implementation = UserBehaviorTypeEnum.class)
    private String behaviorType;
    
    @Schema(description = "分析维度", 
           allowableValues = {"hour", "day", "week", "month"},
           example = "day")
    private String analysisDimension; // 分析维度：hour/day/week/month
} 