package com.example.model.dto.favorite;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class FavoriteFolderDTO {
    private Long id;
    private String name;
    private String description;
    private Boolean isPublic;
    private Integer sort;
    private Integer itemCount;
    private LocalDateTime createTime;
} 