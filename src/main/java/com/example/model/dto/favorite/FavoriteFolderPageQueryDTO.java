package com.example.model.dto.favorite;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.model.enums.FavoriteFolderStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Schema(description = "收藏夹分页查询参数")
@Data
@EqualsAndHashCode(callSuper = true)
public class FavoriteFolderPageQueryDTO extends Page<AdminFavoriteFolderDTO> {
    @Schema(description = "收藏夹名称（模糊查询）", example = "数码")
    private String name;



    @Schema(description = "收藏夹状态", implementation = FavoriteFolderStatusEnum.class)
    private FavoriteFolderStatusEnum status;

    @Schema(description = "是否仅查询自己的", example = "true")
    private Boolean isMine; // 是否只查询自己的
} 