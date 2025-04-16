package com.example.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户查询参数DTO
 */
@Data
public class UserQueryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户名（模糊查询）
     */
    private String username;

    /**
     * 手机号（模糊查询）
     */
    private String phone;

    /**
     * 邮箱（模糊查询）
     */
    private String email;

    /**
     * 用户状态：0-禁用，1-正常
     */
    private Integer status;

    /**
     * 用户角色：1-管理员，2-普通用户
     */
    private Integer role;

    /**
     * 注册开始时间
     */
    private LocalDateTime startTime;

    /**
     * 注册结束时间
     */
    private LocalDateTime endTime;
} 