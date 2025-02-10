package com.example.model.vo.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(name = "商品详情VO")
public class ProductDetailVO {
    @Schema(name = "baseInfo")
    private ProductBaseInfoVO baseInfo;

    @Schema(name = "specs")
    private List<ProductSpecVO> specs;

    @Schema(name = "skus")
    private List<ProductSkuVO> skus;

    @Schema(name = "detailImages")
    private List<String> detailImages;
}





