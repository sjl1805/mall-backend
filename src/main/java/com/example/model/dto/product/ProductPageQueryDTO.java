package com.example.model.dto.product;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.model.enums.ProductStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;


import java.math.BigDecimal;

@Schema(description = "商品分页查询参数")
@Data
@EqualsAndHashCode(callSuper = true)
public class ProductPageQueryDTO extends Page<AdminProductDTO> {
    @Schema(description = "商品名称（模糊查询）", example = "手机")
    private String name;
    @Schema(description = "分类ID", example = "2001")
    private Long categoryId;
    @Schema(description = "最低价格", example = "1000.00")
    private BigDecimal minPrice;


    @Schema(description = "最高价格", example = "5000.00")
    private BigDecimal maxPrice;
    @Schema(description = "商品状态", implementation = ProductStatusEnum.class)
    private ProductStatusEnum status;
    @Schema(description = "是否有库存", example = "true")
    private Boolean hasStock;
} 