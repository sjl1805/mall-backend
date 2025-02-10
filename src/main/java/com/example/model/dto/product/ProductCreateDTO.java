package com.example.model.dto.product;

import com.example.model.enums.ProductStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Schema(description = "商品创建请求参数")
@Data
public class ProductCreateDTO {
    @Schema(description = "商品名称", requiredMode = Schema.RequiredMode.REQUIRED, maxLength = 100, example = "智能手机")
    @NotBlank(message = "商品名称不能为空")
    @Size(max = 100, message = "名称最长100个字符")
    private String name;

    @Schema(description = "分类ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "2001")
    @NotNull(message = "分类ID不能为空")
    private Long categoryId;

    @Schema(description = "商品描述", maxLength = 500, example = "最新款智能手机")
    @Size(max = 500, message = "描述最长500个字符")
    private String description;

    @Schema(description = "商品价格", requiredMode = Schema.RequiredMode.REQUIRED, example = "2999.00")
    @NotNull(message = "价格不能为空")
    @DecimalMin(value = "0.01", message = "价格不能小于0.01")
    private BigDecimal price;

    @Schema(description = "商品库存", example = "100")
    @Min(value = 0, message = "库存不能为负数")
    private Integer stock = 0;

    @Schema(description = "商品图片列表", requiredMode = Schema.RequiredMode.REQUIRED, example = "['image1.jpg','image2.jpg']")
    @NotEmpty(message = "商品图片不能为空")
    private List<String> images;

    @Schema(description = "商品状态", requiredMode = Schema.RequiredMode.REQUIRED, implementation = ProductStatusEnum.class)
    @NotNull(message = "商品状态不能为空")
    private ProductStatusEnum status;
} 