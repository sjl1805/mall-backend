package com.example.model.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum UserRoleEnum {
    GUEST(0, "游客"),
    USER(1, "用户"),
    MERCHANT(2, "商家"),
    ADMIN(9, "管理员");

    @EnumValue
    private final int code;
    private final String description;

    UserRoleEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }
}
