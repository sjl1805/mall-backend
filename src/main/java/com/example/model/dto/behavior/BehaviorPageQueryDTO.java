package com.example.model.dto.behavior;

import com.example.model.enums.UserBehaviorTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "用户行为分页查询参数")
@Data
public class BehaviorPageQueryDTO {
    @Schema(description = "用户ID", example = "12345")
    private Long userId;

    @Schema(description = "商品ID", example = "3001")
    private Long productId;

    @Schema(description = "行为类型", implementation = UserBehaviorTypeEnum.class)
    private UserBehaviorTypeEnum behaviorType;

    @Schema(description = "开始时间", example = "2024-03-01 00:00:00")
    private LocalDateTime startTime;

    @Schema(description = "结束时间", example = "2024-03-31 23:59:59")
    private LocalDateTime endTime;
} 