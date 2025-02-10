package com.example.model.dto.favorite;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "收藏项分页查询参数")
@Data
public class FavoritePageQueryDTO {
    @Schema(description = "指定收藏夹ID", example = "8001")
    private Long folderId; // 指定收藏夹查询
    
    @Schema(description = "商品名称模糊查询", example = "手机")
    private String productName; // 商品名称模糊查询
    
    @Schema(description = "是否只显示有库存", example = "true")
    private Boolean inStock; // 是否只显示有库存
} 