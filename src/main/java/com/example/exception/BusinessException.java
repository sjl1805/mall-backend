package com.example.exception;

import com.example.common.ResultCode;
import lombok.Getter;

/**
 * 业务异常类
 */
@Getter
public class BusinessException extends RuntimeException {

    private final Integer code;

    /**
     * 默认构造函数，使用默认业务错误码
     *
     * @param message 错误消息
     */
    public BusinessException(String message) {
        super(message);
        this.code = ResultCode.BUSINESS_ERROR;
    }

    /**
     * 带错误码的构造函数
     *
     * @param message 错误消息
     * @param code    错误码
     */
    public BusinessException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    /**
     * 带错误码和原因的构造函数
     *
     * @param message 错误消息
     * @param code    错误码
     * @param cause   原始异常
     */
    public BusinessException(String message, Integer code, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

}