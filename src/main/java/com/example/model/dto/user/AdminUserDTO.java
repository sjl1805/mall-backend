package com.example.model.dto.user;

import com.example.model.enums.UserGenderEnum;
import com.example.model.enums.UserRoleEnum;
import com.example.model.enums.UserStatusEnum;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 管理员用用户信息DTO
 */
@Data
public class AdminUserDTO {
    /**
     * 用户ID
     */
    private Long id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 性别
     */
    private UserGenderEnum gender;
    /**
     * 状态
     */
    private UserStatusEnum status;
    /**
     * 角色
     */
    private UserRoleEnum role;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
} 