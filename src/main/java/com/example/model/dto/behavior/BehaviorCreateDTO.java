package com.example.model.dto.behavior;

import com.example.model.enums.UserBehaviorTypeEnum;
import lombok.Data;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import jakarta.validation.constraints.Min;

@Data
public class BehaviorCreateDTO {
    @NotNull(message = "商品ID不能为空")
    private Long productId;

    @NotNull(message = "行为类型不能为空")
    private UserBehaviorTypeEnum behaviorType;

    @NotNull(message = "行为时间不能为空")
    private LocalDateTime behaviorTime;

    @Min(value = 0, message = "停留时长不能为负数")
    private Integer duration = 0;

    private String deviceType;
    private String location;
} 