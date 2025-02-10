package com.example.model.vo.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "用户简略信息VO")
public class UserSimpleVO {
    @Schema(name = "userId", description = "用户ID", example = "10001")
    private Long userId;

    @Schema(name = "nickname", description = "用户昵称", example = "数码达人")
    private String nickname;

    @Schema(name = "avatar", description = "用户头像", example = "https://example.com/avatar.jpg")
    private String avatar;

    @Schema(name = "userLevel", description = "用户等级", example = "2")
    private Integer userLevel;
} 