package com.example.model.dto.sku;

import com.example.model.enums.ProductSkuStatusEnum;
import lombok.Data;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import java.math.BigDecimal;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "SKU更新请求参数")
@Data
public class SkuUpdateDTO {
    @Schema(description = "价格", example = "2799.00")
    @DecimalMin(value = "0.01", message = "价格不能小于0.01")
    private BigDecimal price;

    @Schema(description = "库存", example = "80")
    @Min(value = 0, message = "库存不能为负数")
    private Integer stock;

    @Schema(description = "主图URL", example = "https://example.com/new_main.jpg")
    private String mainImage;

    @Schema(description = "SKU状态", implementation = ProductSkuStatusEnum.class)
    private ProductSkuStatusEnum status;
} 