package com.example.model.dto.user;

import com.example.model.enums.UserGenderEnum;
import lombok.Data;
import jakarta.validation.constraints.Size;

/**
 * 用户信息更新DTO
 */
@Data
public class UserUpdateDTO {
    @Size(max = 20, message = "昵称最长20个字符")
    private String nickname;

    private String avatar;
    private UserGenderEnum gender;
} 