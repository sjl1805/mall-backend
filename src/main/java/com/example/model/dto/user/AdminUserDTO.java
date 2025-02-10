package com.example.model.dto.user;

import com.example.model.enums.UserGenderEnum;
import com.example.model.enums.UserRoleEnum;
import com.example.model.enums.UserStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 管理员用用户信息DTO
 */
@Schema(description = "管理员视角用户详细信息")
@Data
public class AdminUserDTO {
    /**
     * 用户ID
     */
    @Schema(description = "用户ID", example = "1")
    private Long id;
    /**
     * 用户名
     */
    @Schema(description = "用户名", example = "admin")
    private String username;
    /**
     * 昵称
     */
    @Schema(description = "用户昵称", example = "admin")
    private String nickname;
    /**
     * 手机号
     */
    @Schema(description = "手机号", example = "12345678901")
    private String phone;
    /**
     * 邮箱
     */
    @Schema(description = "邮箱", example = "admin@example.com")
    private String email;
    /**
     * 头像
     */
    @Schema(description = "头像", example = "https://example.com/avatar.jpg")
    private String avatar;
    /**
     * 性别
     */
    @Schema(description = "性别", example = "MALE")
    private UserGenderEnum gender;
    /**
     * 状态
     */
    @Schema(description = "状态", example = "ACTIVE")
    private UserStatusEnum status;
    /**
     * 角色
     */
    @Schema(description = "角色", example = "ADMIN")
    private UserRoleEnum role;
    /**
     * 创建时间
     */
    @Schema(description = "创建时间", example = "2024-01-01 12:00:00")
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    @Schema(description = "更新时间", example = "2024-01-01 12:00:00")
    private LocalDateTime updateTime;
    /**
     * 数据版本号（用于乐观锁）
     */
    @Schema(description = "数据版本号（乐观锁）", example = "1")
    private Integer version;
} 