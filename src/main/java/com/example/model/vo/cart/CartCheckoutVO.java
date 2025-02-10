package com.example.model.vo.cart;

import com.example.model.vo.coupon.AvailableCouponVO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * {@code @user} 31815
 * {@code @date}  2025/2/10
 */
@Data
public class CartCheckoutVO {
    private List<CartItemVO> checkedItems;
    private BigDecimal totalAmount;
    private Integer totalQuantity;
    private List<AvailableCouponVO> applicableCoupons;
}