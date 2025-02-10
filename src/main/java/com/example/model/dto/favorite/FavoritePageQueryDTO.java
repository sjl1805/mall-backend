package com.example.model.dto.favorite;

import lombok.Data;

@Data
public class FavoritePageQueryDTO {
    private Long folderId; // 指定收藏夹查询
    private String productName; // 商品名称模糊查询
    private Boolean inStock; // 是否只显示有库存
} 