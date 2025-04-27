package com.example.model.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum GenderEnum {
    UNKNOWN(0, "unknown"),
    MALE(1, "male"),
    FEMALE(2, "female");

    private final int code;
    private final String desc;

    GenderEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @JsonValue
    public int getCode() {
        return code;
    }

    public static GenderEnum fromCode(Integer code) {
        if (code == null) {
            return UNKNOWN;
        }
        for (GenderEnum gender : values()) {
            if (gender.getCode() == code) {
                return gender;
            }
        }
        return UNKNOWN;
    }

    public static GenderEnum fromDesc(String desc) {
        if (desc == null) {
            return UNKNOWN;
        }
        desc = desc.toLowerCase();
        for (GenderEnum gender : values()) {
            if (gender.getDesc().equals(desc)) {
                return gender;
            }
        }
        return UNKNOWN;
    }
} 