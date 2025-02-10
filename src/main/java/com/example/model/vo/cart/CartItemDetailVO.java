package com.example.model.vo.cart;

import com.example.model.vo.product.ProductSkuVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "购物车项详情VO")
public class CartItemDetailVO {
    @Schema(name = "cartItemId", description = "购物车项ID")
    private Long cartItemId;

    @Schema(name = "productInfo", description = "商品基本信息")
    private ProductInfoVO productInfo;

    @Schema(name = "skuInfo", description = "SKU信息")
    private ProductSkuVO skuInfo;

    @Schema(name = "checked", description = "选中状态", example = "true")
    private Boolean checked;

    @Schema(name = "quantity", description = "购买数量", example = "2")
    private Integer quantity;


    @Schema(name = "stockStatus", description = "库存状态", example = "0", allowableValues = {"0", "1"})
    private Integer stockStatus;

    @Schema(name = "invalidStatus", description = "失效状态", example = "0", allowableValues = {"0", "1"})
    private Integer invalidStatus;
} 