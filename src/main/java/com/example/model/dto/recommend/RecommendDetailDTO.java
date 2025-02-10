package com.example.model.dto.recommend;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class RecommendDetailDTO {
    private Long recommendId;
    private String productName;
    private String mainImage;
    private BigDecimal price;
    private Integer type;
    private String typeDesc;
    private Integer sort;
    private String statusDesc;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String algorithmVersion;
    private BigDecimal weight;
    private String recommendReason;
} 