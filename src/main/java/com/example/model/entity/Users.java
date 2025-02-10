package com.example.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 用户表
 *
 * @TableName users
 */
@TableName(value = "users", autoResultMap = true)
@Data
@EqualsAndHashCode(callSuper = true)
public class Users extends BaseEntity {
    /**
     * 用户ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码哈希值
     */
    private String passwordHash;
    /**
     * 加密盐值
     */
    private String salt;
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
     * 性别：0未知 1男 2女
     */
    private Integer gender;
    /**
     * 状态：0禁用 1启用
     */
    private Integer status;
    /**
     *
     */
    private Integer role;
    /**
     * 创建时间（带时区）
     */

    /**
     * 虚拟字段，用于存储创建日期
     */
    private LocalDateTime createDate;

}