package com.example.model.vo.review;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(name = "评价回复VO")
public class ReviewReplyVO {
    @Schema(name = "replyId", description = "回复ID", example = "6001")
    private Long replyId;

    @Schema(name = "content", description = "回复内容", example = "感谢您的评价！")
    private String content;

    @Schema(name = "replyTime", description = "回复时间", example = "2024-03-20T14:30:00")
    private LocalDateTime replyTime;

    @Schema(name = "replierType", description = "回复人类型", example = "1", allowableValues = {"1", "2"})
    private Integer replierType;  // 1-商家 2-用户
} 