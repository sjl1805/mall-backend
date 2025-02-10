package com.example.model.dto.favorite;

import lombok.Data;
import jakarta.validation.constraints.NotNull;

@Data
public class FavoriteAddDTO {
    @NotNull(message = "商品ID不能为空")
    private Long productId;

    private Long folderId; // 可选收藏夹ID
} 