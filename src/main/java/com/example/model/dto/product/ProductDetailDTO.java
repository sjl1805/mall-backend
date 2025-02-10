package com.example.model.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Schema(description = "商品详情信息")
@Data
public class ProductDetailDTO {
    @Schema(description = "商品ID", example = "1001")
    private Long id;
    @Schema(description = "商品名称", example = "智能手机")
    private String name;
    @Schema(description = "分类名称", example = "数码产品")
    private String categoryName;
    @Schema(description = "商品描述", example = "最新款智能手机")
    private String description;
    @Schema(description = "商品价格", example = "2999.00")
    private BigDecimal price;
    @Schema(description = "商品库存", example = "100")
    private Integer stock;
    @Schema(description = "商品图片列表", example = "['image1.jpg','image2.jpg']")
    private List<String> images;
    @Schema(description = "状态描述", example = "已上架")
    private String statusDesc;
    @Schema(description = "平均评分", example = "4.5")
    private Double averageRating;
    @Schema(description = "评价数量", example = "200")
    private Integer reviewCount;
} 