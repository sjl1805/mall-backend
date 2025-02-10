package com.example.model.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum UserBehaviorTypeEnum {
    VIEW(1, "浏览"),
    COLLECT(2, "收藏"),
    BUY(3, "购买"); 

    @EnumValue
    private final int code;
    private final String description;

    UserBehaviorTypeEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }
}

