package com.example.model.dto.favorite;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "添加收藏请求参数")
@Data
public class FavoriteAddDTO {
    @Schema(description = "商品ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "3001")
    @NotNull(message = "商品ID不能为空")
    private Long productId;

    @Schema(description = "目标收藏夹ID", example = "8001")
    private Long folderId; // 可选收藏夹ID
} 