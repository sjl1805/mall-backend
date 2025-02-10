package com.example.model.vo.user;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "头像上传VO")
public class UserAvatarVO {
    @Schema(name = "originalName", description = "原始文件名", example = "avatar.jpg")
    private String originalName;

    @Schema(name = "fileUrl", description = "文件访问URL", example = "https://oss.example.com/avatars/123.jpg")
    private String fileUrl;

    @Schema(name = "fileSize", description = "文件大小（字节）", example = "102400")
    private Long fileSize;

    // Getters and Setters
} 