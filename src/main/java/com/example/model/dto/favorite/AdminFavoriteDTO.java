package com.example.model.dto.favorite;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AdminFavoriteDTO {
    private Long id;
    private Long userId;
    private String userName;
    private Long productId;
    private String productName;
    private String folderName;
    private LocalDateTime createTime;
} 