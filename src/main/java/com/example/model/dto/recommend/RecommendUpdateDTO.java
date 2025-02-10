package com.example.model.dto.recommend;

import com.example.model.enums.RecommendProductStatusEnum;
import lombok.Data;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "推荐商品更新请求")
@Data
public class RecommendUpdateDTO {
    @Schema(description = "排序值（值越大越靠前）", example = "15")
    @Min(value = 0, message = "排序值不能小于0")
    private Integer sort;

    @Schema(description = "推荐状态", implementation = RecommendProductStatusEnum.class)
    private RecommendProductStatusEnum status;

    @Schema(description = "推荐开始时间", example = "2024-03-05T00:00:00")
    @Future(message = "开始时间必须是将来的时间")
    private LocalDateTime startTime;

    @Schema(description = "推荐结束时间", example = "2024-04-05T00:00:00")
    @Future(message = "结束时间必须是将来的时间")
    private LocalDateTime endTime;

    @Schema(description = "算法版本号（格式vX.Y.Z）", example = "v1.2.4")
    @Pattern(regexp = "^v\\d+(\\.\\d+){2}$", message = "版本号格式不正确")
    private String algorithmVersion;

    @Schema(description = "推荐权重（0-1之间）", example = "0.90")
    @DecimalMin(value = "0.0", inclusive = false, message = "权重必须大于0")
    @DecimalMax(value = "1.0", inclusive = false, message = "权重必须小于1")
    private BigDecimal weight;

    @Schema(description = "推荐理由", maxLength = 100, example = "用户好评率98%")
    @Size(max = 100, message = "推荐理由最长100个字符")
    private String recommendReason;
} 