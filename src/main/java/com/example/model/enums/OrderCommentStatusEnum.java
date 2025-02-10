package com.example.model.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum OrderCommentStatusEnum {
    /**
     * 未评价
     */
    UNCOMMENTED(0, "未评价"),
    /**
     * 已评价
     */
    COMMENTED(1, "已评价");

    @EnumValue
    private final int code;
    private final String description;

    OrderCommentStatusEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }
}
