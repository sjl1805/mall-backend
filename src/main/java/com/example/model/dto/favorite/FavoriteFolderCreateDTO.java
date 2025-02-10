package com.example.model.dto.favorite;

import com.example.model.enums.FavoriteFolderStatusEnum;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "收藏夹创建请求参数")
@Data
public class FavoriteFolderCreateDTO {
    @Schema(description = "收藏夹名称", requiredMode = Schema.RequiredMode.REQUIRED, maxLength = 32, example = "数码收藏")
    @NotBlank(message = "收藏夹名称不能为空")
    @Size(max = 32, message = "名称最长32个字符")
    private String name;

    @Schema(description = "收藏夹描述", maxLength = 128, example = "我的数码产品收藏")
    @Size(max = 128, message = "描述最长128个字符")
    private String description;

    @Schema(description = "公开状态", requiredMode = Schema.RequiredMode.REQUIRED, implementation = FavoriteFolderStatusEnum.class)
    @NotNull(message = "公开状态不能为空")
    private FavoriteFolderStatusEnum isPublic;
} 