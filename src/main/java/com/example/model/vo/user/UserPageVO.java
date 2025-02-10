package com.example.model.vo.user;

import com.example.model.enums.UserRoleEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * {@code @user} 31815
 * {@code @date}  2025/2/10
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserPageVO extends UserBasicVO {
    private String phone;
    private String email;
    private UserRoleEnum role;
}