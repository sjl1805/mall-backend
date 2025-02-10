package com.example.model.dto.recommend;

import com.example.model.enums.RecommendProductStatusEnum;
import com.example.model.enums.RecommendTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Schema(description = "推荐商品创建请求")
@Data
public class RecommendCreateDTO {
    @Schema(description = "商品ID", requiredMode = RequiredMode.REQUIRED, example = "2001")
    @NotNull(message = "商品ID不能为空")
    private Long productId;

    @Schema(description = "推荐类型", requiredMode = RequiredMode.REQUIRED, implementation = RecommendTypeEnum.class)
    @NotNull(message = "推荐类型不能为空")
    private RecommendTypeEnum type;

    @Schema(description = "排序值（值越大越靠前）", example = "10")
    @Min(value = 0, message = "排序值不能小于0")
    private Integer sort = 0;

    @Schema(description = "推荐状态", requiredMode = RequiredMode.REQUIRED, implementation = RecommendProductStatusEnum.class)
    @NotNull(message = "状态不能为空")
    private RecommendProductStatusEnum status;


    @Schema(description = "推荐开始时间", requiredMode = RequiredMode.REQUIRED, example = "2024-03-01T00:00:00")
    @Future(message = "开始时间必须是将来的时间")
    @NotNull(message = "开始时间不能为空")
    private LocalDateTime startTime;

    @Schema(description = "推荐结束时间", requiredMode = RequiredMode.REQUIRED, example = "2024-04-01T00:00:00")
    @Future(message = "结束时间必须是将来的时间")
    @NotNull(message = "结束时间不能为空")
    private LocalDateTime endTime;

    @Schema(description = "算法版本号（格式vX.Y.Z）", example = "v1.2.3")
    @NotBlank(message = "算法版本不能为空")
    @Pattern(regexp = "^v\\d+(\\.\\d+){2}$", message = "版本号格式不正确")
    private String algorithmVersion;

    @Schema(description = "推荐权重（0-1之间）", example = "0.85")
    @DecimalMin(value = "0.0", inclusive = false, message = "权重必须大于0")
    @DecimalMax(value = "1.0", inclusive = false, message = "权重必须小于1")
    private BigDecimal weight;

    @Schema(description = "推荐理由", maxLength = 100, example = "本月销量冠军")
    @Size(max = 100, message = "推荐理由最长100个字符")
    private String recommendReason;
} 