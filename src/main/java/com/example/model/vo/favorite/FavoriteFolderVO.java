package com.example.model.vo.favorite;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(name = "收藏夹VO")
public class FavoriteFolderVO {
    @Schema(name = "folderId", description = "收藏夹ID", example = "1001")
    private Long folderId;

    @Schema(name = "name", description = "收藏夹名称", example = "我的数码收藏")
    private String name;

    @Schema(name = "description", description = "收藏夹描述", example = "数码产品收藏")
    private String description;

    @Schema(name = "itemCount", description = "收藏商品数量", example = "5")
    private Integer itemCount;

    @Schema(name = "createTime", description = "创建时间", example = "2024-03-20T10:00:00")
    private LocalDateTime createTime;

    @Schema(name = "isPublic", description = "是否公开", example = "false")
    private Boolean isPublic;
} 