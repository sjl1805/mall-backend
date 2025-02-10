package com.example.config;

import com.example.common.Result;
import com.example.common.ResultCode;
import com.example.exception.BusinessException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 处理业务异常
    @ExceptionHandler(BusinessException.class)
    public Result<?> handleBusinessException(BusinessException e, HttpServletRequest request) {
        log.warn("业务异常 => URI: {} | 错误码: {} | 错误信息: {}",
                request.getRequestURI(), e.getResultCode().getCode(), e.getMessage());
        return Result.error(e.getResultCode().getCode(), e.getMessage());
    }


    // 处理参数校验异常
    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public Result<?> handleValidationException(Exception e) {
        String errorMsg = "参数校验失败";
        //Object errorSource = null;

        if (e instanceof MethodArgumentNotValidException) {
            FieldError fieldError = ((MethodArgumentNotValidException) e).getBindingResult().getFieldError();
            errorMsg = fieldError != null ? fieldError.getDefaultMessage() : "无效的请求参数";
        } else if (e instanceof BindException) {
            FieldError fieldError = ((BindException) e).getBindingResult().getFieldError();
            errorMsg = fieldError != null ? fieldError.getDefaultMessage() : "无效的请求参数";
        }
        return Result.error(ResultCode.BAD_REQUEST.getCode(), errorMsg);
    }


    // 处理请求参数格式错误
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result<?> handleHttpMessageNotReadable(HttpMessageNotReadableException e) {
        log.error("请求参数解析失败: {}", e.getMessage());
        return Result.error(ResultCode.BAD_REQUEST.getCode(), "请求参数格式错误");
    }


    // 处理其他未捕获异常
    @ExceptionHandler(Exception.class)
    public Result<?> handleException(Exception e, HttpServletRequest request) {
        log.error("系统异常 => URI: {} | 错误信息: {}", request.getRequestURI(), e.getMessage(), e);
        return Result.error(ResultCode.INTERNAL_ERROR.getCode(), e.getMessage());
    }


    // 处理JSR303校验异常
    @ExceptionHandler(ConstraintViolationException.class)
    public Result<?> handleConstraintViolationException(ConstraintViolationException e) {
        String errorMsg = e.getConstraintViolations().iterator().next().getMessage();
        return Result.error(ResultCode.BAD_REQUEST.getCode(), errorMsg);
    }
} 