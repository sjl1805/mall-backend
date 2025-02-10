package com.example.model.vo.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
@Schema(name = "商品SKU VO")
public class ProductSkuVO {
    @Schema(name = "skuId", description = "SKU ID", example = "2001")
    private Long skuId;

    @Schema(name = "specCombination", description = "规格组合", example = "{\"颜色\":\"黑色\",\"内存\":\"128GB\"}")
    private Map<String, String> specCombination;

    @Schema(name = "price", description = "价格", example = "2999.00")
    private BigDecimal price;

    @Schema(name = "stock", description = "库存", example = "100")
    private Integer stock;

    @Schema(name = "mainImage", description = "SKU主图", example = "https://example.com/sku.jpg")
    private String mainImage;
} 