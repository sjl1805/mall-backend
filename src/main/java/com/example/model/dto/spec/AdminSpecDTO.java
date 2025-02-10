package com.example.model.dto.spec;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "管理员视角规格详情")
@Data
public class AdminSpecDTO {
    @Schema(description = "规格ID", example = "7001")
    private Long id;

    @Schema(description = "商品ID", example = "3001")
    private Long productId;

    @Schema(description = "商品名称", example = "智能手机")
    private String productName;

    @Schema(description = "规格名称", example = "颜色")
    private String specName;

    @Schema(description = "规格值列表", example = "[\"白色\",\"黑色\"]")
    private List<String> specValues;

    @Schema(description = "创建时间", example = "2024-03-01 10:00:00")
    private LocalDateTime createTime;

    @Schema(description = "更新时间", example = "2024-03-05 15:30:00")
    private LocalDateTime updateTime;
} 