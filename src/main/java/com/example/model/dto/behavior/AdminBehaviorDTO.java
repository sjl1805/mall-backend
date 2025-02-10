package com.example.model.dto.behavior;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AdminBehaviorDTO {
    private Long id;
    private Long userId;
    private String userName;
    private Long productId;
    private String productName;
    private String behaviorType;
    private LocalDateTime behaviorTime;
    private Integer duration;
    private String deviceType;
    private String location;
} 