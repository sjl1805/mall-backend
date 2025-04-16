package com.example.model.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 购物车视图对象
 */
@Data
public class CartVO {
    /**
     * 购物车项列表
     */
    private List<CartItemVO> cartItems;

    /**
     * 已选中商品数量
     */
    private Integer selectedCount;

    /**
     * 已选中商品总价
     */
    private BigDecimal selectedTotalPrice;

    /**
     * 所有商品总价
     */
    private BigDecimal totalPrice;

    /**
     * 全选状态：true-全选，false-非全选
     */
    private Boolean allChecked;
} 