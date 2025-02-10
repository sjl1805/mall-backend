package com.example.model.enums;

import lombok.Getter;
/**
 * 推荐类型枚举
 */
@Getter
public enum RecommendTypeEnum {
    HOT(1, "热门商品"),

    NEW_ARRIVAL(2, "新品推荐");


    private final int code;
    private final String desc;

    RecommendTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
