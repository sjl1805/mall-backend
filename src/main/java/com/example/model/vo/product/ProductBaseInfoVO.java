package com.example.model.vo.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;


@Data
@Schema(name = "商品基础信息VO")
class ProductBaseInfoVO {
    @Schema(name = "商品名称", example = "智能手机")

    private String productName;

    @Schema(name = "商品描述", example = "旗舰级智能手机")
    private String description;

    @Schema(name = "分类信息")
    private CategoryInfoVO category;

    @Schema(name = "基础价格", example = "2999.00")
    private BigDecimal basePrice;
}