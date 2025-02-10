package com.example.model.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum UserGenderEnum {
    UNKNOWN(0, "未知"),
    MALE(1, "男"),
    FEMALE(2, "女");

    @EnumValue
    private final int code;
    private final String description;

    UserGenderEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }
}
