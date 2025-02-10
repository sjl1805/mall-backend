package com.example.model.dto.order;

import com.example.model.enums.OrderPaymentMethodEnum;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data

public class OrderPayDTO {
    @NotBlank(message = "订单号不能为空")
    private String orderNo;

    @NotNull(message = "支付方式不能为空")
    private OrderPaymentMethodEnum paymentMethod;
} 