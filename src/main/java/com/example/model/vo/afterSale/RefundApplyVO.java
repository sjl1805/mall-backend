package com.example.model.vo.afterSale;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
@Schema(name = "退款申请VO")
public class RefundApplyVO {
    @Schema(name = "orderId", description = "订单ID", required = true, example = "10001")
    @NotNull(message = "订单ID不能为空")
    private Long orderId;

    @Schema(name = "reason", description = "退款原因", required = true, example = "商品质量问题")
    @NotBlank(message = "请填写退款原因")
    @Size(max = 200, message = "原因最长200个字符")
    private String reason;

    @Schema(name = "proofImages", description = "凭证图片", example = "[\"https://example.com/proof1.jpg\"]")
    private List<String> proofImages;
} 