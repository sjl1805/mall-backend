package com.example.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * 用户登录返回VO
 */
@Data
@Builder
public class UserLoginVO {
    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 性别：0-未知，1-男，2-女
     */
    private Integer gender;

    /**
     * 角色：1-管理员，2-用户
     */
    private Integer role;

    private Date lastLoginTime;

    /*
     * 注册时间
     */
    private Date registerTime;

    /**
     * 认证令牌
     */
    private String token;
} 