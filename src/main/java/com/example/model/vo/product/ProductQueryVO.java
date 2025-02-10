package com.example.model.vo.product;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import java.math.BigDecimal;

@Data
@Schema(name = "商品查询VO")
public class ProductQueryVO {
    @Schema(name = "分类ID", example = "1001")
    private Long categoryId;

    @Schema(name = "价格区间最小值", example = "100.00")
    @DecimalMin("0.00")
    private BigDecimal minPrice;

    @Schema(name = "价格区间最大值", example = "5000.00")
    @DecimalMin("0.00")
    private BigDecimal maxPrice;

    @Schema(name = "排序字段", example = "price", allowableValues = {"price", "sales", "create_time"})
    private String sortBy;

    @Schema(name = "排序方式", example = "asc", allowableValues = {"asc", "desc"})
    private String sortOrder;

    @Schema(name = "当前页码", example = "1")
    @Min(1)
    private Integer pageNum = 1;

    @Schema(name = "每页数量", example = "10")
    @Range(min = 1, max = 100)
    private Integer pageSize = 10;
} 