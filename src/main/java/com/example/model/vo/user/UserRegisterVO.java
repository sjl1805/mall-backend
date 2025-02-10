package com.example.model.vo.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * 用户注册VO
 */
@Schema(name = "用户注册VO")
public class UserRegisterVO {
    @Schema(name = "username", description = "用户名", example = "testUser", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "用户名不能为空")
    @Size(min = 4, max = 20, message = "用户名长度4-20位")
    private String username;

    @Schema(name = "password", description = "密码", example = "Test1234", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,20}$", 
             message = "密码需8-20位且包含字母和数字")
    private String password;

    @Schema(name = "phone", description = "手机号", example = "13800138000", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phone;

    @Schema(name = "email", description = "邮箱", example = "test@example.com")
    @jakarta.validation.constraints.Email(message = "邮箱格式不正确")
    private String email;

    @Schema(name = "captcha", description = "图形验证码", example = "ABCD", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "验证码不能为空")
    private String captcha;

    // Getters and Setters
} 