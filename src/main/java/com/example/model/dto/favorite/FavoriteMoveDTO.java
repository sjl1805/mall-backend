package com.example.model.dto.favorite;

import lombok.Data;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import io.swagger.v3.oas.annotations.media.Schema;


@Schema(description = "移动收藏项请求参数")
@Data
public class FavoriteMoveDTO {
    @Schema(description = "目标收藏夹ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "8002")
    @NotNull(message = "目标收藏夹不能为空")
    private Long targetFolderId;

    @Schema(description = "需要移动的收藏项ID列表", requiredMode = Schema.RequiredMode.REQUIRED, example = "[7001,7002]")
    @NotNull(message = "收藏项不能为空")
    private List<Long> favoriteIds;
} 