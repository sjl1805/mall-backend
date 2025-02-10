package com.example.model.dto.favorite;

import com.example.model.enums.FavoriteFolderStatusEnum;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "收藏夹分页查询参数")
@Data
public class FavoriteFolderPageQueryDTO {
    @Schema(description = "收藏夹名称（模糊查询）", example = "数码")
    private String name;
    
    @Schema(description = "收藏夹状态", implementation = FavoriteFolderStatusEnum.class)
    private FavoriteFolderStatusEnum status;
    
    @Schema(description = "是否仅查询自己的", example = "true")
    private Boolean isMine; // 是否只查询自己的
} 