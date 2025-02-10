package com.example.model.vo.product;

import com.example.model.enums.ReviewStatusEnum;

import java.time.LocalDateTime;

/**
 * {@code @user} 31815
 * {@code @date}  2025/2/10
 */
public class ProductReviewDetailVO extends ProductReviewVO {
    private String auditRemark;
    private LocalDateTime auditTime;
    private ReviewStatusEnum status;
}
