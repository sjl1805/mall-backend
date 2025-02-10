package com.example.model.dto.review;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ReviewDetailDTO {
    private Long reviewId;
    private String userName;
    private String productName;
    private Integer rating;
    private String content;
    private List<String> images;
    private String statusDesc;
    private LocalDateTime createTime;
    private LocalDateTime auditTime;
} 