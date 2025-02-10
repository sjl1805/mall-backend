package com.example.model.dto.sku;

import com.example.model.enums.ProductSkuStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Schema(description = "SKU创建请求参数")
@Data
public class SkuCreateDTO {
    @Schema(description = "商品ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "3001")
    @NotNull(message = "商品ID不能为空")
    private Long productId;

    @Schema(description = "规格组合", requiredMode = Schema.RequiredMode.REQUIRED, example = "颜色:白色;内存:256GB")
    @NotBlank(message = "规格组合不能为空")
    private String specValues;

    @Schema(description = "价格", requiredMode = Schema.RequiredMode.REQUIRED, example = "2999.00")
    @NotNull(message = "价格不能为空")
    @DecimalMin(value = "0.01", message = "价格不能小于0.01")
    private BigDecimal price;

    @Schema(description = "初始库存", example = "100")
    @Min(value = 0, message = "库存不能为负数")
    private Integer stock = 0;

    @Schema(description = "主图URL", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://example.com/main.jpg")
    @NotBlank(message = "主图不能为空")
    private String mainImage;

    @Schema(description = "SKU状态", requiredMode = Schema.RequiredMode.REQUIRED, implementation = ProductSkuStatusEnum.class)
    @NotNull(message = "状态不能为空")
    private ProductSkuStatusEnum status;
} 