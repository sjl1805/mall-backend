package com.example.model.dto.recommend;

import com.example.model.enums.RecommendProductStatusEnum;
import lombok.Data;
import java.time.LocalDateTime;
import com.example.model.enums.RecommendTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;


@Schema(description = "推荐商品分页查询参数")
@Data
public class RecommendPageQueryDTO {
    @Schema(description = "商品ID", example = "2001")
    private Long productId;
    
    @Schema(description = "推荐类型", implementation = RecommendTypeEnum.class)
    private RecommendTypeEnum type;
    
    @Schema(description = "推荐状态", implementation = RecommendProductStatusEnum.class)
    private RecommendProductStatusEnum status;
    

    @Schema(description = "算法版本", example = "v1.2.3")
    private String algorithmVersion;
    
    @Schema(description = "开始时间查询起始", example = "2024-03-01T00:00:00")
    private LocalDateTime startTime;
    
    @Schema(description = "结束时间查询截止", example = "2024-04-01T00:00:00")
    private LocalDateTime endTime;
} 