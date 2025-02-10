package com.example.model.dto.review;

import com.example.model.enums.ReviewStatusEnum;
import lombok.Data;
import jakarta.validation.constraints.NotNull;

@Data
public class ReviewAuditDTO {
    @NotNull(message = "审核状态不能为空")
    private ReviewStatusEnum status;

    private String auditRemark; // 审核备注
} 