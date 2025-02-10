package com.example.model.dto.recommend;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "推荐商品管理端详情")
@Data
public class AdminRecommendDTO {
    @Schema(description = "推荐记录ID", example = "1001")
    private Long id;

    @Schema(description = "商品ID", example = "2001")
    private Long productId;

    @Schema(description = "商品名称", example = "旗舰智能手机")
    private String productName;

    @Schema(description = "推荐类型编码", example = "1")
    private Integer type;

    @Schema(description = "推荐类型说明", example = "热门商品")
    private String typeDesc;

    @Schema(description = "排序值", example = "10")
    private Integer sort;

    @Schema(description = "状态说明", example = "生效中")
    private String statusDesc;

    @Schema(description = "推荐开始时间", example = "2024-03-01 00:00:00")
    private LocalDateTime startTime;

    @Schema(description = "推荐结束时间", example = "2024-04-01 00:00:00")
    private LocalDateTime endTime;

    @Schema(description = "算法版本", example = "v1.2.3")
    private String algorithmVersion;

    @Schema(description = "权重值", example = "0.85")
    private BigDecimal weight;

    @Schema(description = "推荐理由", example = "本月销量冠军")
    private String recommendReason;

    @Schema(description = "创建时间", example = "2024-02-28 10:00:00")
    private LocalDateTime createTime;

    @Schema(description = "更新时间", example = "2024-03-05 15:30:00")
    private LocalDateTime updateTime;
} 