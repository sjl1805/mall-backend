package com.example.model.vo.user;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "登录成功VO")
public class UserLoginSuccessVO {
    @Schema(name = "用户ID", example = "123456")
    private Long userId;

    @Schema(name = "访问令牌", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String accessToken;

    @Schema(name = "用户基本信息")
    private UserProfileVO profile;

    // Getters and Setters
} 