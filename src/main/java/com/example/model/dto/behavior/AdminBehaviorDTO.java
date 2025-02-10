package com.example.model.dto.behavior;

import com.example.model.enums.UserBehaviorTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理员视角用户行为记录")
@Data
public class AdminBehaviorDTO {
    @Schema(description = "记录ID", example = "10001")
    private Long id;

    @Schema(description = "用户ID", example = "12345")
    private Long userId;

    @Schema(description = "用户名", example = "john_doe")
    private String userName;

    @Schema(description = "商品ID", example = "3001")
    private Long productId;

    @Schema(description = "商品名称", example = "智能手机")
    private String productName;

    @Schema(description = "行为类型", implementation = UserBehaviorTypeEnum.class)
    private String behaviorType;

    @Schema(description = "行为时间", example = "2024-03-15 10:00:00")
    private LocalDateTime behaviorTime;

    @Schema(description = "停留时长(秒)", example = "120")
    private Integer duration;

    @Schema(description = "设备类型", example = "iOS")
    private String deviceType;

    @Schema(description = "地理位置", example = "北京市朝阳区")
    private String location;
} 