package com.example.model.enums;

import lombok.Getter;

@Getter
public enum CategoryStatusEnum {
    DISABLED(0, "禁用"),
    ENABLED(1, "启用");

    private final int code;
    private final String description;

    CategoryStatusEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public static CategoryStatusEnum of(int code) {
        for (CategoryStatusEnum status : values()) {
            if (status.code == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("无效的分类状态码: " + code);
    }
} 