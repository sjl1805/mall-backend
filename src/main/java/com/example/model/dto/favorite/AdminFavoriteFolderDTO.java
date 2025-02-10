package com.example.model.dto.favorite;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AdminFavoriteFolderDTO {
    private Long id;
    private Long userId;
    private String userName;
    private String folderName;
    private Integer itemCount;
    private Boolean isPublic;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
} 