package com.example.model.dto.sku;

import com.example.model.enums.ProductSkuStatusEnum;
import lombok.Data;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Data
public class SkuCreateDTO {
    @NotNull(message = "商品ID不能为空")
    private Long productId;

    @NotBlank(message = "规格组合不能为空")
    private String specValues;

    @NotNull(message = "价格不能为空")
    @DecimalMin(value = "0.01", message = "价格不能小于0.01")
    private BigDecimal price;

    @Min(value = 0, message = "库存不能为负数")
    private Integer stock = 0;

    @NotBlank(message = "主图不能为空")
    private String mainImage;

    @NotNull(message = "状态不能为空")
    private ProductSkuStatusEnum status;
} 