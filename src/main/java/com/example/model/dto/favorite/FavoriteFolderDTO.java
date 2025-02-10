package com.example.model.dto.favorite;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "收藏夹基本信息")
@Data
public class FavoriteFolderDTO {
    @Schema(description = "收藏夹ID", example = "8001")
    private Long id;

    @Schema(description = "收藏夹名称", example = "数码产品")
    private String name;

    @Schema(description = "收藏夹描述", example = "各类数码产品收藏")
    private String description;

    @Schema(description = "是否公开", example = "true")
    private Boolean isPublic;

    @Schema(description = "排序值", example = "1")
    private Integer sort;

    @Schema(description = "收藏项数量", example = "10")
    private Integer itemCount;

    @Schema(description = "创建时间", example = "2024-03-01 10:00:00")
    private LocalDateTime createTime;
} 