package com.example.model.dto.favorite;

import com.example.model.enums.FavoriteFolderStatusEnum;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;

@Data
public class FavoriteFolderCreateDTO {
    @NotBlank(message = "收藏夹名称不能为空")
    @Size(max = 32, message = "名称最长32个字符")
    private String name;

    @Size(max = 128, message = "描述最长128个字符")
    private String description;

    @NotNull(message = "公开状态不能为空")
    private FavoriteFolderStatusEnum isPublic;
} 