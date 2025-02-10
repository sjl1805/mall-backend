package com.example.model.vo.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(name = "商品规格VO")
class ProductSpecVO {
    @Schema(name = "规格名称", example = "颜色")

    private String specName;

    @Schema(name = "规格值列表", example = "[\"黑色\",\"白色\"]")
    private List<String> specValues;
}
