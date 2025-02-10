package com.example.model.dto.favorite;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理员视角收藏夹详情")
@Data
public class AdminFavoriteFolderDTO {
    @Schema(description = "收藏夹ID", example = "8001")
    private Long id;

    @Schema(description = "用户ID", example = "12345")
    private Long userId;

    @Schema(description = "用户名", example = "john_doe")
    private String userName;

    @Schema(description = "收藏夹名称", example = "数码产品")
    private String folderName;

    @Schema(description = "收藏项数量", example = "10")
    private Integer itemCount;

    @Schema(description = "是否公开", example = "true")
    private Boolean isPublic;

    @Schema(description = "创建时间", example = "2024-03-01 10:00:00")
    private LocalDateTime createTime;

    @Schema(description = "更新时间", example = "2024-03-05 15:30:00")
    private LocalDateTime updateTime;
} 