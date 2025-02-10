package com.example.common;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "状态码枚举")
public enum ResultCode {
    // 基础状态码
    SUCCESS(200, "操作成功"),
    BAD_REQUEST(400, "请求参数错误"),
    UNAUTHORIZED(401, "未授权访问"),
    FORBIDDEN(403, "访问被禁止"),
    NOT_FOUND(404, "资源未找到"),
    INTERNAL_ERROR(500, "服务器内部错误"),

    // 用户模块 10xxxx
    USER_NOT_EXIST(100001, "用户不存在"),
    USER_LOGIN_FAIL(100002, "登录失败"),
    USER_REGISTER_CONFLICT(100003, "用户已存在"),
    USER_PASSWORD_ERROR(100004, "密码错误"),
    USER_CAPTCHA_ERROR(100005, "验证码错误"),
    USER_ADDRESS_LIMIT(100006, "收货地址已达上限"),

    // 商品模块 20xxxx
    PRODUCT_OFF_SHELF(200001, "商品已下架"),
    PRODUCT_STOCK_SHORTAGE(200002, "库存不足"),
    PRODUCT_SKU_INVALID(200003, "无效的商品规格"),
    PRODUCT_CATEGORY_DISABLED(200004, "分类已禁用"),

    // 订单模块 30xxxx 
    ORDER_STATUS_ERROR(300001, "订单状态异常"),
    ORDER_CREATE_FAIL(300002, "订单创建失败"),
    ORDER_ITEM_INVALID(300003, "包含无效的商品项"),
    ORDER_PAY_EXPIRED(300004, "支付超时"),

    // 支付模块 40xxxx
    PAYMENT_FAILED(400001, "支付失败"),
    PAYMENT_CHANNEL_ERROR(400002, "支付渠道异常"),
    PAYMENT_AMOUNT_MISMATCH(400003, "金额不一致"),

    // 购物车模块 50xxxx
    CART_ITEM_LIMIT(500001, "购物车商品数量超限"),
    CART_ITEM_INVALID(500002, "购物车商品失效"),

    // 优惠券模块 60xxxx
    COUPON_EXPIRED(600001, "优惠券已过期"),
    COUPON_USED(600002, "优惠券已使用"),
    COUPON_CONDITION_UNMET(600003, "不满足使用条件"),

    // 评价模块 70xxxx
    REVIEW_ALREADY_EXISTS(700001, "已评价过该商品"),
    REVIEW_CONTENT_SENSITIVE(700002, "评价包含敏感词"),

    // 售后模块 80xxxx
    REFUND_APPLY_EXPIRED(800001, "超出退款申请期限"),
    REFUND_STATUS_ERROR(800002, "退款状态异常"),

    // 系统通用 90xxxx
    FILE_UPLOAD_FAIL(900001, "文件上传失败"),
    SMS_SEND_FAIL(900002, "短信发送失败"),
    RATE_LIMIT_EXCEEDED(900003, "请求过于频繁"),
    PARAMETER_VALIDATION_FAILED(900004, "参数校验失败");


    @Schema(name = "code", description = "状态码", example = "200")
    private final int code;
    

    @Schema(name = "message", description = "状态信息", example = "操作成功")
    private final String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
} 