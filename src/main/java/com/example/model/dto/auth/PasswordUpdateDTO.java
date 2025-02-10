package com.example.model.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;


/**
 * 密码修改DTO
 */
@Schema(description = "密码修改请求参数")
@Data
public class PasswordUpdateDTO {
    @Schema(description = "原密码", requiredMode = Schema.RequiredMode.REQUIRED, example = "oldPassword123")
    @NotBlank(message = "原密码不能为空")
    private String oldPassword;

    @Schema(description = "新密码", requiredMode = Schema.RequiredMode.REQUIRED, minLength = 6, maxLength = 20, example = "newSecurePwd123")
    @NotBlank(message = "新密码不能为空")
    @Size(min = 6, max = 20, message = "新密码长度6-20位")
    private String newPassword;

    @Schema(description = "确认密码", requiredMode = Schema.RequiredMode.REQUIRED, example = "newSecurePwd123")
    @NotBlank(message = "确认密码不能为空")
    private String confirmPassword;
} 