package com.example.model.dto.user;

import com.example.model.enums.UserGenderEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户信息更新DTO
 */
@Schema(description = "用户信息更新请求参数")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDTO {
    @Schema(description = "用户昵称", maxLength = 20, example = "NewNickname")
    @Size(max = 20, message = "昵称最长20个字符")
    private String nickname;

    @Schema(description = "头像URL", example = "https://example.com/avatar.jpg")
    @Pattern(regexp = "^(http|https)://.*$", message = "头像必须是合法的URL地址")
    private String avatar;

    @Schema(description = "性别", implementation = UserGenderEnum.class, requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "性别不能为空")
    private UserGenderEnum gender;
} 