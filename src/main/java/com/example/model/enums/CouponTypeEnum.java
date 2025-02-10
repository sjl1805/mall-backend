package com.example.model.enums;

import lombok.Getter;

@Getter
public enum CouponTypeEnum {
    FULL_REDUCTION(1, "满减券"),
    DISCOUNT(2, "折扣券");

    private final int code;
    private final String description;

    CouponTypeEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public static CouponTypeEnum of(int code) {
        for (CouponTypeEnum type : values()) {
            if (type.code == code) {
                return type;
            }
        }
        throw new IllegalArgumentException("无效的优惠券类型码: " + code);
    }
} 