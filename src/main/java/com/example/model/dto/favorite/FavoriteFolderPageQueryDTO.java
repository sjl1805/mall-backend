package com.example.model.dto.favorite;

import com.example.model.enums.FavoriteFolderStatusEnum;
import lombok.Data;

@Data
public class FavoriteFolderPageQueryDTO {
    private String name;
    private FavoriteFolderStatusEnum status;
    private Boolean isMine; // 是否只查询自己的
} 