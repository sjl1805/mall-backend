package com.example.model.vo.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.Map;

@Schema(name = "购物车商品VO")
public class CartItemVO {
    @Schema(name = "cartId", description = "购物车项ID", example = "1")
    private Long cartId;
    
    @Schema(name = "productId", description = "商品ID", example = "1001")
    private Long productId;
    
    @Schema(name = "skuId", description = "SKU ID", example = "2001")
    private Long skuId;
    
    @Schema(name = "productName", description = "商品名称", example = "智能手机")
    private String productName;
    
    @Schema(name = "productImage", description = "商品主图", example = "https://example.com/image.jpg")
    private String productImage;
    
    @Schema(name = "specs", description = "规格属性")
    private Map<String, String> specs;
    
    @Schema(name = "price", description = "单价", example = "2999.00")
    private BigDecimal price;
    
    @Schema(name = "quantity", description = "数量", example = "2")
    @Min(value = 1, message = "数量至少1件")
    @Max(value = 99, message = "单商品最多99件")
    private Integer quantity;
    
    @Schema(name = "checked", description = "选中状态", example = "true")
    private Boolean checked;

    // Getters and Setters
} 