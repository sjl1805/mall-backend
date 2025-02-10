package com.example.model.dto.review;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AdminReviewDTO {
    private Long id;
    private Long userId;
    private String userName;
    private Long productId;
    private String productName;
    private Integer rating;
    private String content;
    private String auditRemark;
    private LocalDateTime createTime;
    private LocalDateTime auditTime;
} 