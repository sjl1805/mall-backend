package com.example.model.vo.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(name = "物流轨迹VO")
public class LogisticsTraceVO {
    @Schema(name = "time", description = "时间", example = "2024-03-20T14:30:00")
    private LocalDateTime time;

    @Schema(name = "description", description = "描述", example = "已签收，感谢使用顺丰速运")
    private String description;

    @Schema(name = "location", description = "当前位置", example = "深圳市南山区")
    private String location;
} 