package com.example.model.dto.favorite;

import lombok.Data;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@Data
public class FavoriteMoveDTO {
    @NotNull(message = "目标收藏夹不能为空")
    private Long targetFolderId;

    @NotNull(message = "收藏项不能为空")
    private List<Long> favoriteIds;
} 