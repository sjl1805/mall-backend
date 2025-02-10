package com.example.model.dto.auth;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Schema;
/**
 * 用户注册DTO
 */
@Data
public class UserRegisterDTO {
    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    @Size(min = 4, max = 20, message = "用户名长度4-20位")
    @Schema(description = "用户名", example = "admin")
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 20, message = "密码长度6-20位")
    @Schema(description = "密码", example = "123456")
    private String password;


    /**
     * 手机号
     */
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    @Schema(description = "手机号", example = "12345678901")
    private String phone;


    /**
     * 邮箱
     */

    @Pattern(regexp = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$", message = "邮箱格式不正确")
    @Schema(description = "邮箱", example = "john@example.com")
    private String email;
} 