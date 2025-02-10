package com.example.model.dto.spec;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class AdminSpecDTO {
    private Long id;
    private Long productId;
    private String productName;
    private String specName;
    private List<String> specValues;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
} 