package com.example.model.dto.cart;

import com.example.model.enums.CartCheckedStatusEnum;
import lombok.Data;

@Data
public class CartPageQueryDTO {
    private String productName;
    private CartCheckedStatusEnum checkedStatus;
    private Boolean inStock; // 是否只查有库存
} 