package com.example.model.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum ReviewStatusEnum {
    PENDING(0, "待审核"),
    APPROVED(1, "已通过"),
    REJECTED(2, "已拒绝");

    @EnumValue
    private final int code;
    private final String description;

    ReviewStatusEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public static ReviewStatusEnum of(int code) {
        for (ReviewStatusEnum status : values()) {
            if (status.code == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("无效的审核状态码: " + code);
    }
} 