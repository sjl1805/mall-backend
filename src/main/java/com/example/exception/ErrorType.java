package com.example.exception;

import org.springframework.http.HttpStatus;

public enum ErrorType {
    // 系统级错误
    SYSTEM_ERROR(1000, "系统内部错误", HttpStatus.INTERNAL_SERVER_ERROR),
    

    // 业务错误
    PRODUCT_NOT_FOUND(2001, "商品不存在", HttpStatus.NOT_FOUND),
    INSUFFICIENT_STOCK(2002, "库存不足", HttpStatus.BAD_REQUEST),
    
    // 参数校验错误
    INVALID_PARAMETER(3000, "参数校验失败", HttpStatus.BAD_REQUEST),
    
    // 安全相关错误
    UNAUTHORIZED(4001, "未授权访问", HttpStatus.UNAUTHORIZED),
    ACCESS_DENIED(4003, "访问被拒绝", HttpStatus.FORBIDDEN),
    
    // 用户相关错误
    USERNAME_EXISTS(2001, "用户名已存在", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(2002, "用户不存在", HttpStatus.NOT_FOUND),

INVALID_CREDENTIALS(2003, "用户名或密码错误", HttpStatus.UNAUTHORIZED),
USER_DISABLED(2004, "用户已被禁用", HttpStatus.FORBIDDEN),
INVALID_PASSWORD(2005, "原密码不正确", HttpStatus.BAD_REQUEST);

    private final int code;
    private final String message;
    private final HttpStatus httpStatus;

    ErrorType(int code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    // Getters
    public int getCode() { return code; }
    public String getMessage() { return message; }
    public HttpStatus getHttpStatus() { return httpStatus; }
} 