package com.example.model.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum UserStatusEnum {
    ACTIVE(1, "正常"),
    INACTIVE(0, "禁用");

    @EnumValue
    private final int code;
    private final String description;

    UserStatusEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }
}
