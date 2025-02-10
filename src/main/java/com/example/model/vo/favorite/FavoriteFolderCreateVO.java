package com.example.model.vo.favorite;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(name = "收藏夹创建VO")
public class FavoriteFolderCreateVO {
    @Schema(name = "name", description = "收藏夹名称", required = true, example = "我的数码收藏")
    @NotBlank(message = "收藏夹名称不能为空")
    @Size(max = 20, message = "名称最长20个字符")
    private String name;

    @Schema(name = "description", description = "收藏夹描述", example = "数码产品收藏")
    @Size(max = 100, message = "描述最长100个字符")
    private String description;

    @Schema(name = "isPublic", description = "是否公开", example = "false")
    private Boolean isPublic = false;
} 