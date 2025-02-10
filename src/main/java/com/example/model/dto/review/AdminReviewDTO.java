package com.example.model.dto.review;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理员视角评价详情")
@Data
public class AdminReviewDTO {
    @Schema(description = "评价ID", example = "5001")
    private Long id;

    @Schema(description = "用户ID", example = "12345")
    private Long userId;

    @Schema(description = "用户名", example = "john_doe")
    private String userName;

    @Schema(description = "商品ID", example = "3001")
    private Long productId;

    @Schema(description = "商品名称", example = "智能手机")
    private String productName;

    @Schema(description = "评分", example = "5")
    private Integer rating;

    @Schema(description = "评价内容", example = "非常好用！")
    private String content;

    @Schema(description = "审核备注", example = "符合规范")
    private String auditRemark;

    @Schema(description = "创建时间", example = "2024-03-15 10:00:00")
    private LocalDateTime createTime;

    @Schema(description = "审核时间", example = "2024-03-16 09:30:00")
    private LocalDateTime auditTime;
} 