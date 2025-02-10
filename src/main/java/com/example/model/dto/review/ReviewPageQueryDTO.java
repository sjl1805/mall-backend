package com.example.model.dto.review;

import com.example.model.enums.ReviewStatusEnum;
import lombok.Data;

@Data
public class ReviewPageQueryDTO {
    private Long productId;
    private String keyword;
    private ReviewStatusEnum status;
    private Boolean hasImage;
} 