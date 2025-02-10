package com.example.model.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum RatingEnum {
    ONE_STAR(1, "1分"),
    TWO_STARS(2, "2分"),
    THREE_STARS(3, "3分"),
    FOUR_STARS(4, "4分"),
    FIVE_STARS(5, "5分");

    @EnumValue
    private final int code;
    private final String description;

    RatingEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public static RatingEnum of(int code) {
        for (RatingEnum rating : values()) {
            if (rating.code == code) {
                return rating;
            }
        }
        throw new IllegalArgumentException("无效的评分值: " + code);
    }
} 