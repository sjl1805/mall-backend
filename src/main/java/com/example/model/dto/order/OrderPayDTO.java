package com.example.model.dto.order;

import com.example.model.enums.OrderPaymentMethodEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "订单支付请求参数")
@Data
public class OrderPayDTO {
    @Schema(description = "订单编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "20240315123456")
    @NotBlank(message = "订单号不能为空")
    private String orderNo;

    @Schema(description = "支付方式", requiredMode = Schema.RequiredMode.REQUIRED, implementation = OrderPaymentMethodEnum.class)
    @NotNull(message = "支付方式不能为空")
    private OrderPaymentMethodEnum paymentMethod;
} 