package com.example.model.dto.auth;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

/**
 * 用户登录DTO
 */
@Data
public class UserLoginDTO {
    @NotBlank(message = "登录账号不能为空")
    private String login; // 可以是用户名/手机/邮箱

    @NotBlank(message = "密码不能为空")
    private String password;
} 