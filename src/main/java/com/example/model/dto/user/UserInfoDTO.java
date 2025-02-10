package com.example.model.dto.user;

import com.example.model.enums.UserGenderEnum;
import com.example.model.enums.UserRoleEnum;
import com.example.model.enums.UserStatusEnum;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 用户信息DTO
 */
@Data
public class UserInfoDTO {
    private Long id;
    private String username;
    private String nickname;
    private String phone;
    private String email;
    private String avatar;
    private UserGenderEnum gender;
    private UserStatusEnum status;
    private UserRoleEnum role;
    private LocalDateTime createTime;
} 