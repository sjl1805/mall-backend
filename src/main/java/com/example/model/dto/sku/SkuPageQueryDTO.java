package com.example.model.dto.sku;

import com.example.model.dto.PaginationDTO;
import com.example.model.enums.ProductSkuStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Schema(description = "SKU分页查询参数")
@Data
@EqualsAndHashCode(callSuper = true)

public class SkuPageQueryDTO extends PaginationDTO<AdminSkuDTO> {
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