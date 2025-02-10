package com.example.model.dto.review;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "评价详情信息")
@Data
public class ReviewDetailDTO {
    @Schema(description = "评价ID", example = "5001")
    private Long reviewId;
    @Schema(description = "用户名", example = "john_doe")
    private String userName;
    @Schema(description = "商品名称", example = "智能手机")
    private String productName;
    @Schema(description = "评分", example = "5")
    private Integer rating;
    @Schema(description = "评价内容", example = "非常好用！")
    private String content;
    @Schema(description = "评价图片列表", example = "['image1.jpg','image2.jpg']")
    private List<String> images;
    @Schema(description = "状态描述", example = "已通过")
    private String statusDesc;
    @Schema(description = "创建时间", example = "2024-03-15 10:00:00")
    private LocalDateTime createTime;
    @Schema(description = "审核时间", example = "2024-03-16 09:30:00")
    private LocalDateTime auditTime;
} 