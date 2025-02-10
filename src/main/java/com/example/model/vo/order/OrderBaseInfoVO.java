package com.example.model.vo.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(name = "订单基础信息VO")
public class OrderBaseInfoVO {
    @Schema(name = "orderNo", description = "订单编号", example = "20240320123456")
    private String orderNo;

    @Schema(name = "status", description = "订单状态", example = "1")
    private Integer status;

    @Schema(name = "createTime", description = "创建时间", example = "2024-03-20T10:00:00")
    private LocalDateTime createTime;

    @Schema(name = "paymentTime", description = "支付时间", example = "2024-03-20T10:05:00")
    private LocalDateTime paymentTime;

    @Schema(name = "deliveryTime", description = "发货时间", example = "2024-03-20T12:00:00")
    private LocalDateTime deliveryTime;
}
