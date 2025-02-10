package com.example.model.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "管理员视角订单项详情")
@Data
public class AdminOrderItemDTO {
    @Schema(description = "订单项ID", example = "5001")
    private Long id;

    @Schema(description = "订单编号", example = "20240315123456")
    private String orderNo;

    @Schema(description = "用户ID", example = "12345")
    private Long userId;

    @Schema(description = "商品名称", example = "智能手机")
    private String productName;

    @Schema(description = "商品单价", example = "2999.00")
    private BigDecimal price;

    @Schema(description = "购买数量", example = "2")
    private Integer quantity;

    @Schema(description = "总金额", example = "5998.00")
    private BigDecimal totalAmount;

    @Schema(description = "创建时间", example = "2024-03-15 10:00:00")
    private LocalDateTime createTime;
} 