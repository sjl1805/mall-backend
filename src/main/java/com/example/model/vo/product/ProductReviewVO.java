package com.example.model.vo.product;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;


/**
 * {@code @user} 31815
 * {@code @date}  2025/2/10
 */
@Data
public class ProductReviewVO {
    private String username;
    private String avatar;
    private Integer rating;
    private String content;
    private List<String> images;

    private LocalDateTime createTime;
    private ProductSimpleVO productInfo;
}