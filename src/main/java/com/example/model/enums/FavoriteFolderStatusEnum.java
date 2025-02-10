package com.example.model.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum FavoriteFolderStatusEnum {
    PRIVATE(0, "私密"),
    PUBLIC(1, "公开");

    @EnumValue
    private final int code;
    private final String description;

    FavoriteFolderStatusEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }
} 