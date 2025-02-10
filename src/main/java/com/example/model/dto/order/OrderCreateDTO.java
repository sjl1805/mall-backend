package com.example.model.dto.order;

import com.example.model.enums.OrderPaymentMethodEnum;
import lombok.Data;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@Data
public class OrderCreateDTO {
    @NotEmpty(message = "订单项不能为空")
    private List<OrderItemCreateDTO> items;

    @NotNull(message = "收货地址ID不能为空")
    private Long addressId;

    private Long couponId;

    @NotNull(message = "支付方式不能为空")
    private OrderPaymentMethodEnum paymentMethod;
} 