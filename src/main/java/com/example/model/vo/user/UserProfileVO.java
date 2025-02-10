package com.example.model.vo.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Range;


/**
 * 用户资料VO
 */
public class UserProfileVO {
    @Schema(name = "用户ID", example = "123456")
    private Long id;


    @Schema(name = "昵称", example = "测试用户")
    @Size(max = 20, message = "昵称最长20个字符")
    private String nickname;


    @Schema(name = "头像URL", example = "https://example.com/avatar.jpg")
    private String avatar;


    @Schema(name = "性别 0-未知 1-男 2-女", example = "1")
    @Range(min = 0, max = 2, message = "性别参数不合法")
    private Integer gender;


    @Schema(name = "绑定手机号", example = "13800138000")
    private String phone;


    @Schema(name = "绑定邮箱", example = "test@example.com")
    private String email;


    // Getters and Setters
} 