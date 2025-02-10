package com.example.model.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum ProductSkuStatusEnum {
    OFFLINE(0, "下架"),
    ONLINE(1, "上架");  

    @EnumValue
    private final int code;
    private final String description;

    ProductSkuStatusEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }
}
