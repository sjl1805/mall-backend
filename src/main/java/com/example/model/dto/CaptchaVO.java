package com.example.model.dto;

import lombok.Builder;
import lombok.Data;

/**
 * 验证码返回VO
 */
@Data
@Builder
public class CaptchaVO {
    /**
     * 验证码的key，用于验证
     */
    private String key;

    /**
     * 验证码图片Base64编码
     */
    private String image;
} 