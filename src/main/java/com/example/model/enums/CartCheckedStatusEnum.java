package com.example.model.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum CartCheckedStatusEnum {
    UNCHECKED(0, "未选中"),
    CHECKED(1, "已选中");

    @EnumValue
    private final int code;
    private final String description;

    CartCheckedStatusEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public static CartCheckedStatusEnum of(int code) {
        for (CartCheckedStatusEnum status : values()) {
            if (status.code == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("无效的选中状态码: " + code);
    }
} 