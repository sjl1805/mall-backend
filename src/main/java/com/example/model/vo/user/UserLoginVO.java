package com.example.model.vo.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

/**
 * 用户登录VO
 */
@Schema(name = "用户登录VO")
public class UserLoginVO {
    @Schema(name = "identifier", description = "登录凭证（用户名/手机号/邮箱）",
            example = "testUser", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "登录凭证不能为空")
    private String identifier;

    @Schema(name = "credential", description = "密码或验证码",
            example = "Test1234", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "密码或验证码不能为空")
    private String credential;

    @Schema(name = "loginType", description = "登录类型：password-密码 sms-短信",
            example = "password")
    private String loginType;

} 