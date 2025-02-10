package com.example.model.dto.order;

import com.example.model.enums.OrderPaymentMethodEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Schema(description = "订单创建请求参数")
@Data
public class OrderCreateDTO {
    @Schema(description = "订单项列表", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "订单项不能为空")
    private List<OrderItemCreateDTO> items;

    @Schema(description = "收货地址ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1001")
    @NotNull(message = "收货地址ID不能为空")
    private Long addressId;

    @Schema(description = "优惠券ID", example = "2001")
    private Long couponId;

    @Schema(description = "支付方式", requiredMode = Schema.RequiredMode.REQUIRED, implementation = OrderPaymentMethodEnum.class)
    @NotNull(message = "支付方式不能为空")
    private OrderPaymentMethodEnum paymentMethod;
} 