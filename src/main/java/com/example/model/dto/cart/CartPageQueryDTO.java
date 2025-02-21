package com.example.model.dto.cart;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.example.model.enums.CartCheckedStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Schema(description = "购物车分页查询参数")
@Data
@EqualsAndHashCode(callSuper = true)
public class CartPageQueryDTO extends Page<CartItemDTO> {
    @Schema(description = "商品名称模糊查询", example = "手机")
    private String productName;

    @Schema(description = "选中状态过滤", implementation = CartCheckedStatusEnum.class)
    private CartCheckedStatusEnum checkedStatus;

    @Schema(description = "是否仅显示有库存商品", example = "true")
    private Boolean inStock;
} 