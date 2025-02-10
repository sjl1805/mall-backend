package com.example.model.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "订单详情响应数据")
@Data
public class OrderDetailDTO {
    @Schema(description = "订单编号", example = "20240315123456")
    private String orderNo;

    @Schema(description = "订单总金额", example = "999.99")
    private BigDecimal totalAmount;

    @Schema(description = "实付金额", example = "899.99")
    private BigDecimal payAmount;

    @Schema(description = "订单状态描述", example = "已支付")
    private String statusDesc;

    @Schema(description = "收货人姓名", example = "张三")
    private String receiverName;

    @Schema(description = "收货人电话", example = "13800138000")
    private String receiverPhone;

    @Schema(description = "收货地址", example = "北京市朝阳区")
    private String receiverAddress;

    @Schema(description = "支付时间", example = "2024-03-15 10:05:00")
    private LocalDateTime paymentTime;

    @Schema(description = "物流公司", example = "顺丰速运")
    private String logisticsCompany;

    @Schema(description = "物流单号", example = "SF123456789")
    private String trackingNumber;

    @Schema(description = "订单项列表")
    private List<OrderItemDTO> items;
} 