package com.example.model.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 用户登录DTO
 */
@Schema(description = "用户登录请求参数")
@Data
public class UserLoginDTO {
    @Schema(description = "登录标识（用户名/手机/邮箱）", requiredMode = Schema.RequiredMode.REQUIRED, example = "user@example.com")
    @NotBlank(message = "登录标识不能为空")
    @Size(min = 3, max = 50, message = "登录标识长度3-50位")
    private String loginId;  // 将字段名从login改为loginId更明确

    @Schema(description = "密码", requiredMode = Schema.RequiredMode.REQUIRED, example = "password123")
    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 20, message = "密码长度6-20位")
    private String password;
} 