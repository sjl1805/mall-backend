package com.example.model.vo.product;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * {@code @user} 31815
 * {@code @date}  2025/2/10
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ProductDetailVO extends ProductSimpleVO {
    private String description;
    private List<String> images;
    private List<ProductSkuVO> skus;
    private List<ProductSpecVO> specs;
}
