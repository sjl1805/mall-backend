package com.example.model.dto.behavior;

import com.example.model.enums.UserBehaviorTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "用户行为记录创建参数")
@Data
public class BehaviorCreateDTO {
    @Schema(description = "商品ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "3001")
    @NotNull(message = "商品ID不能为空")
    private Long productId;

    @Schema(description = "行为类型", requiredMode = Schema.RequiredMode.REQUIRED,
            implementation = UserBehaviorTypeEnum.class)
    @NotNull(message = "行为类型不能为空")
    private UserBehaviorTypeEnum behaviorType;

    @Schema(description = "行为时间", requiredMode = Schema.RequiredMode.REQUIRED,
            example = "2024-03-15 10:00:00")
    @NotNull(message = "行为时间不能为空")
    private LocalDateTime behaviorTime;

    @Schema(description = "停留时长(秒)", minimum = "0", example = "60")
    @Min(value = 0, message = "停留时长不能为负数")
    private Integer duration = 0;

    @Schema(description = "设备类型", example = "Android")
    private String deviceType;

    @Schema(description = "地理位置", example = "上海市浦东新区")
    private String location;
} 