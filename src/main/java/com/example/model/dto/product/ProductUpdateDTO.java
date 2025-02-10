package com.example.model.dto.product;

import com.example.model.enums.ProductStatusEnum;
import lombok.Data;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
@Data
public class ProductUpdateDTO {
    @Size(max = 100, message = "名称最长100个字符")
    private String name;

    private Long categoryId;

    @Size(max = 500, message = "描述最长500个字符")
    private String description;

    @DecimalMin(value = "0.01", message = "价格不能小于0.01")
    private BigDecimal price;

    @Min(value = 0, message = "库存不能为负数")
    private Integer stock;

    private List<String> images;

    private ProductStatusEnum status;
} 