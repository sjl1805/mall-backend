package com.example.model.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum OrderPaymentMethodEnum {
    /**
     * 微信支付
     */
    WECHAT(1, "微信支付"),
    /**
     * 支付宝支付
     */
    ALIPAY(2, "支付宝支付"),
    /**
     * 银联支付
     */
    UNIONPAY(3, "银联支付");

    @EnumValue
    private final int code;
    private final String description;


    OrderPaymentMethodEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }
}
