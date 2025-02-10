package com.example.model.dto.behavior;

import com.example.model.enums.UserBehaviorTypeEnum;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class BehaviorPageQueryDTO {
    private Long userId;
    private Long productId;
    private UserBehaviorTypeEnum behaviorType;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
} 