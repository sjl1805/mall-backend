package com.example.model.vo.logistics;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(name = "物流信息VO")
public class LogisticsInfoVO {
    @Schema(name = "company", description = "物流公司", example = "顺丰速运")
    private String company;

    @Schema(name = "trackingNumber", description = "运单号", example = "SF123456789")
    private String trackingNumber;

    @Schema(name = "logisticsStatus", description = "物流状态", example = "1", allowableValues = {"0", "1", "2", "3"})
    private Integer logisticsStatus;

    @Schema(name = "traces", description = "物流轨迹")
    private List<LogisticsTraceVO> traces;
} 