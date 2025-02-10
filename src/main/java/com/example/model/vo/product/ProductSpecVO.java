package com.example.model.vo.product;

import lombok.Data;

import java.util.List;

/**
 * {@code @user} 31815
 * {@code @date}  2025/2/10
 */
@Data
public class ProductSpecVO {
    private String specName;
    private List<String> specValues;
}