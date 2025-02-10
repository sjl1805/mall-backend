package com.example.model.dto.product;

import com.example.model.enums.ProductStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Schema(description = "商品更新请求参数")
@Data
public class ProductUpdateDTO {
    @Schema(description = "商品名称", maxLength = 100, example = "智能手机（升级版）")
    @Size(max = 100, message = "名称最长100个字符")
    private String name;

    @Schema(description = "分类ID", example = "2001")
    private Long categoryId;

    @Schema(description = "商品描述", maxLength = 500, example = "升级款智能手机")
    @Size(max = 500, message = "描述最长500个字符")
    private String description;

    @Schema(description = "商品价格", example = "3299.00")
    @DecimalMin(value = "0.01", message = "价格不能小于0.01")
    private BigDecimal price;

    @Schema(description = "商品库存", example = "150")
    @Min(value = 0, message = "库存不能为负数")
    private Integer stock;

    @Schema(description = "商品图片列表", example = "['new_image1.jpg','new_image2.jpg']")
    private List<String> images;

    @Schema(description = "商品状态", implementation = ProductStatusEnum.class)
    private ProductStatusEnum status;
} 