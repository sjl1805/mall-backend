package com.example.model.dto.cart;

import com.example.model.enums.CartCheckedStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Schema(description = "购物车项详细信息")
@Data
public class CartItemDTO {
    @Schema(description = "购物车项ID", example = "5001")
    private Long cartId;

    @Schema(description = "商品ID", example = "3001")
    private Long productId;

    @Schema(description = "商品名称", example = "智能手机")
    private String productName;

    @Schema(description = "商品主图URL", example = "https://example.com/product.jpg")
    private String mainImage;

    @Schema(description = "商品单价", example = "2999.00")
    private BigDecimal price;

    @Schema(description = "购买数量", example = "2")
    private Integer quantity;

    @Schema(description = "总金额", example = "5998.00")
    private BigDecimal totalAmount;

    @Schema(description = "选中状态", implementation = CartCheckedStatusEnum.class)
    private CartCheckedStatusEnum checked;

    @Schema(description = "当前库存", example = "100")
    private Integer stock;
} 