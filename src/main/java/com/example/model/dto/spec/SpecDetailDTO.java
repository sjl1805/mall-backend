package com.example.model.dto.spec;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema(description = "规格详情信息")
@Data
public class SpecDetailDTO {
    @Schema(description = "规格ID", example = "7001")
    private Long id;
    @Schema(description = "商品名称", example = "智能手机")
    private String productName;
    @Schema(description = "规格名称", example = "颜色")
    private String specName;
    @Schema(description = "规格值列表", example = "[\"白色\",\"黑色\"]")
    private List<String> specValues;
} 