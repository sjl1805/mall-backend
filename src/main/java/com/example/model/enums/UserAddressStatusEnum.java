package com.example.model.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum UserAddressStatusEnum {
    NOT_DEFAULT(0, "非默认"),
    DEFAULT(1, "默认");

    @EnumValue
    private final int code;
    private final String description;

    UserAddressStatusEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }
}
