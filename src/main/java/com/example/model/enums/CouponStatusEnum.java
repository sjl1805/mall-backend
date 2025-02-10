package com.example.model.enums;

import lombok.Getter;
import com.baomidou.mybatisplus.annotation.EnumValue;

@Getter
public enum CouponStatusEnum {
    INVALID(0, "失效"),
    VALID(1, "生效");

    @EnumValue
    private final int code;
    private final String description;

    CouponStatusEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public static CouponStatusEnum of(int code) {
        for (CouponStatusEnum status : values()) {
            if (status.code == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("无效的优惠券状态码: " + code);
    }
} 