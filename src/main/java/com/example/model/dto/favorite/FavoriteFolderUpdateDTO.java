package com.example.model.dto.favorite;

import com.example.model.enums.FavoriteFolderStatusEnum;
import lombok.Data;
import jakarta.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "收藏夹更新请求参数")
@Data
public class FavoriteFolderUpdateDTO {
    @Schema(description = "收藏夹名称", maxLength = 32, example = "数码产品（更新）")
    @Size(max = 32, message = "名称最长32个字符")
    private String name;

    @Schema(description = "收藏夹描述", maxLength = 128, example = "更新后的数码产品收藏")
    @Size(max = 128, message = "描述最长128个字符")
    private String description;

    @Schema(description = "公开状态", implementation = FavoriteFolderStatusEnum.class)
    private FavoriteFolderStatusEnum isPublic;
} 