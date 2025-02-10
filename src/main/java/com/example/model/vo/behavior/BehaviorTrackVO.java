package com.example.model.vo.behavior;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(name = "行为跟踪VO")
public class BehaviorTrackVO {
    @Schema(name = "productId", description = "商品ID", required = true, example = "1001")
    @NotNull(message = "商品ID不能为空")
    private Long productId;

    @Schema(name = "behaviorType", description = "行为类型", required = true, example = "view", allowableValues = {"view", "collect", "cart_add", "buy"})
    @Pattern(regexp = "view|collect|cart_add|buy", message = "无效行为类型")
    private String behaviorType;

    @Schema(name = "duration", description = "停留时长（秒）", example = "30")
    @Min(0)
    private Integer duration = 0;

    @Schema(name = "deviceFingerprint", description = "设备指纹", example = "abcd1234")
    private String deviceFingerprint;

    @Schema(name = "referer", description = "页面来源", example = "/product/1001")
    private String referer;
} 