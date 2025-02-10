package com.example.model.dto.sku;

import com.example.model.enums.ProductSkuStatusEnum;
import lombok.Data;
import java.math.BigDecimal;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "SKU分页查询参数")
@Data
public class SkuPageQueryDTO {
    @Schema(description = "商品ID", example = "3001")
    private Long productId;
    @Schema(description = "规格关键词（模糊查询）", example = "白色")
    private String specKeyword;
    @Schema(description = "最低价格", example = "1000.00")
    private BigDecimal minPrice;
    @Schema(description = "最高价格", example = "5000.00")
    private BigDecimal maxPrice;
    @Schema(description = "SKU状态", implementation = ProductSkuStatusEnum.class)
    private ProductSkuStatusEnum status;
} 