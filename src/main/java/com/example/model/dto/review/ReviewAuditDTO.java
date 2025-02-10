package com.example.model.dto.review;

import com.example.model.enums.ReviewStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "评价审核请求参数")
@Data
public class ReviewAuditDTO {
    @Schema(description = "审核状态", requiredMode = Schema.RequiredMode.REQUIRED, implementation = ReviewStatusEnum.class)
    @NotNull(message = "审核状态不能为空")
    private ReviewStatusEnum status;

    @Schema(description = "审核备注", maxLength = 200, example = "包含敏感词")
    private String auditRemark; // 审核备注
} 