package com.example.model.vo.user;

import com.example.model.enums.UserGenderEnum;
import com.example.model.enums.UserStatusEnum;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * {@code @user} 31815
 * {@code @date}  2025/2/10
 */
@Data
public class UserBasicVO {
    private Long id;
    private String username;
    private String nickname;
    private String avatar;
    private UserGenderEnum gender;

    private UserStatusEnum status;
    private LocalDateTime createTime;
}