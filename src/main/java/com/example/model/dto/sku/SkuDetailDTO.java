package com.example.model.dto.sku;

import lombok.Data;
import java.math.BigDecimal;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "SKU详情信息")
@Data
public class SkuDetailDTO {
    @Schema(description = "SKU ID", example = "9001")
    private Long skuId;
    @Schema(description = "商品名称", example = "智能手机")
    private String productName;
    @Schema(description = "规格组合", example = "颜色:白色;内存:256GB")
    private String specValues;
    @Schema(description = "价格", example = "2999.00")
    private BigDecimal price;
    @Schema(description = "当前库存", example = "50")
    private Integer stock;
    @Schema(description = "累计销量", example = "200")
    private Integer sales;
    @Schema(description = "主图URL", example = "https://example.com/main.jpg")
    private String mainImage;
    @Schema(description = "状态描述", example = "已上架")
    private String statusDesc;
} 