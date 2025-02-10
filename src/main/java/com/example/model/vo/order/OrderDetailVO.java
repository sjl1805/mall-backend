package com.example.model.vo.order;

import com.example.model.vo.logistics.LogisticsInfoVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Data
@Schema(name = "订单详情VO")
public class OrderDetailVO {
    @Schema(name = "orderNo", description = "订单编号", example = "20240320123456")
    private String orderNo;

    @Schema(name = "status", description = "订单状态", example = "1")
    private Integer status;

    @Schema(name = "receiver", description = "收货人信息")
    private ReceiverInfoVO receiver;

    @Schema(name = "paymentMethod", description = "支付方式", example = "1")
    private Integer paymentMethod;

    @Schema(name = "paymentTime", description = "支付时间", example = "2024-03-20T10:05:00")
    private LocalDateTime paymentTime;

    @Schema(name = "items", description = "商品项列表")
    private List<OrderItemVO> items;

    @Schema(name = "logistics", description = "物流信息")
    private LogisticsInfoVO logistics;

    @Schema(name = "couponInfo", description = "优惠信息")
    private CouponInfoVO couponInfo;

    @Schema(name = "totalAmount", description = "订单总金额", example = "2999.00")
    private BigDecimal totalAmount;

    @Schema(name = "discountAmount", description = "优惠金额", example = "100.00")
    private BigDecimal discountAmount;

    @Schema(name = "payAmount", description = "实付金额", example = "2899.00")
    private BigDecimal payAmount;


}
