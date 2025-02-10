package com.example.model.dto.recommend;

import com.example.model.enums.RecommendProductStatusEnum;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class RecommendPageQueryDTO {
    private Long productId;
    private Integer type;
    private RecommendProductStatusEnum status;
    private String algorithmVersion;
    private LocalDateTime startTimeBegin;
    private LocalDateTime endTimeEnd;
} 