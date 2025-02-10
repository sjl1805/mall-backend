package com.example.model.dto.favorite;

import com.example.model.enums.FavoriteFolderStatusEnum;
import lombok.Data;
import jakarta.validation.constraints.Size;

@Data
public class FavoriteFolderUpdateDTO {
    @Size(max = 32, message = "名称最长32个字符")
    private String name;

    @Size(max = 128, message = "描述最长128个字符")
    private String description;

    private FavoriteFolderStatusEnum isPublic;
} 