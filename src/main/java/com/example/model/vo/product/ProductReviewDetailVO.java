package com.example.model.vo.product;

import com.example.model.enums.ReviewStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * {@code @user} 31815
 * {@code @date}  2025/2/10
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ProductReviewDetailVO extends ProductReviewVO {
    private String auditRemark;
    private LocalDateTime auditTime;
    private ReviewStatusEnum status;
}


