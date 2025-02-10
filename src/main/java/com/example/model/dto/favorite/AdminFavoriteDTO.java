package com.example.model.dto.favorite;

import lombok.Data;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "管理员视角收藏项详情")
@Data
public class AdminFavoriteDTO {
    @Schema(description = "收藏项ID", example = "7001")
    private Long id;
    
    @Schema(description = "用户ID", example = "1")
    private Long userId;
    

    @Schema(description = "用户名", example = "admin")
    private String userName;
    
    @Schema(description = "商品ID", example = "1")
    private Long productId;
    

    @Schema(description = "商品名称", example = "智能手机")
    private String productName;
    
    @Schema(description = "收藏夹名称", example = "我的最爱")

    private String folderName;
    
    @Schema(description = "收藏时间", example = "2024-03-15 10:00:00")
    private LocalDateTime createTime;
} 