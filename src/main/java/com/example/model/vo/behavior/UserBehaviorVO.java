package com.example.model.vo.behavior;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(name = "用户行为记录VO")
public class UserBehaviorVO {
    @Schema(name = "productId", description = "商品ID", required = true, example = "1001")
    @NotNull(message = "商品ID不能为空")
    private Long productId;

    @Schema(name = "behaviorType", description = "行为类型", required = true, example = "view", allowableValues = {"view", "collect", "buy"})
    @Pattern(regexp = "view|collect|buy", message = "无效的行为类型")
    private String behaviorType;

    @Schema(name = "duration", description = "停留时长（秒）", example = "30")
    @Min(0)
    private Integer duration;

    @Schema(name = "deviceType", description = "设备类型", example = "mobile")
    private String deviceType;
} 