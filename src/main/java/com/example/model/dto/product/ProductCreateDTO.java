package com.example.model.dto.product;

import com.example.model.enums.ProductStatusEnum;
import lombok.Data;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductCreateDTO {
    @NotBlank(message = "商品名称不能为空")
    @Size(max = 100, message = "名称最长100个字符")
    private String name;

    @NotNull(message = "分类ID不能为空")
    private Long categoryId;

    @Size(max = 500, message = "描述最长500个字符")
    private String description;

    @NotNull(message = "价格不能为空")
    @DecimalMin(value = "0.01", message = "价格不能小于0.01")
    private BigDecimal price;

    @Min(value = 0, message = "库存不能为负数")
    private Integer stock = 0;

    @NotEmpty(message = "商品图片不能为空")
    private List<String> images;

    @NotNull(message = "商品状态不能为空")
    private ProductStatusEnum status;
} 