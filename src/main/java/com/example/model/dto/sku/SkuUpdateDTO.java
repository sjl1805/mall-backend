package com.example.model.dto.sku;

import com.example.model.enums.ProductSkuStatusEnum;
import lombok.Data;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import java.math.BigDecimal;

@Data
public class SkuUpdateDTO {
    @DecimalMin(value = "0.01", message = "价格不能小于0.01")
    private BigDecimal price;

    @Min(value = 0, message = "库存不能为负数")
    private Integer stock;

    private String mainImage;

    private ProductSkuStatusEnum status;
} 