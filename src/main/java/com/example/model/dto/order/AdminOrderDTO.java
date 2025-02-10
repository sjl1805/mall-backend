package com.example.model.dto.order;

import com.example.model.enums.OrderStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "管理员视角订单详情")
@Data
public class AdminOrderDTO {
    @Schema(description = "订单ID", example = "1000001")
    private Long id;

    @Schema(description = "订单编号", example = "20240315123456")
    private String orderNo;

    @Schema(description = "用户ID", example = "12345")
    private Long userId;

    @Schema(description = "订单总金额", example = "999.99")
    private BigDecimal totalAmount;

    @Schema(description = "实付金额", example = "899.99")
    private BigDecimal payAmount;

    @Schema(description = "订单状态", implementation = OrderStatusEnum.class)
    private OrderStatusEnum status;

    @Schema(description = "支付方式", example = "ALIPAY")
    private String paymentMethod;

    @Schema(description = "创建时间", example = "2024-03-15 10:00:00")
    private LocalDateTime createTime;

    @Schema(description = "支付时间", example = "2024-03-15 10:05:00")
    private LocalDateTime paymentTime;

    @Schema(description = "发货时间", example = "2024-03-15 10:30:00")
    private LocalDateTime deliveryTime;

    @Schema(description = "物流信息", example = "顺丰速运 SF123456789")
    private String logisticsInfo;
} 