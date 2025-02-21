package com.example.exception;

import com.example.common.ResultCode;

public class BusinessException extends RuntimeException {
    private final ResultCode resultCode;


    public BusinessException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.resultCode = resultCode;
    }


    public BusinessException(ResultCode resultCode, String message) {
        super(message);
        this.resultCode = resultCode;
    }


    public ResultCode getResultCode() {
        return resultCode;
    }
} 