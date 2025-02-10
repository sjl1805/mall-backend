package com.example.model.dto.recommend;

import com.example.model.enums.RecommendProductStatusEnum;
import lombok.Data;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class RecommendCreateDTO {
    @NotNull(message = "商品ID不能为空")
    private Long productId;

    @NotNull(message = "推荐类型不能为空")
    @Min(value = 1, message = "推荐类型最小为1")
    @Max(value = 2, message = "推荐类型最大为2")
    private Integer type;

    @Min(value = 0, message = "排序值不能小于0")
    private Integer sort = 0;

    @NotNull(message = "状态不能为空")
    private RecommendProductStatusEnum status;

    @Future(message = "开始时间必须是将来的时间")
    private LocalDateTime startTime;

    @Future(message = "结束时间必须是将来的时间")
    private LocalDateTime endTime;

    @NotBlank(message = "算法版本不能为空")
    @Pattern(regexp = "^v\\d+(\\.\\d+){2}$", message = "版本号格式不正确")
    private String algorithmVersion;

    @DecimalMin(value = "0.0", message = "权重不能为负数")
    private BigDecimal weight = BigDecimal.ZERO;

    @Size(max = 100, message = "推荐理由最长100个字符")
    private String recommendReason;
} 