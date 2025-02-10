package com.example.exception;

import com.example.exception.core.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.Map;
import java.util.stream.Collectors;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // 处理自定义业务异常
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handleBaseException(BaseException ex) {
        logError(ex);
        return buildResponse(ex.getErrorType(), ex.getContext(), ex);
    }

    // 处理参数校验异常
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        FieldError::getDefaultMessage,
                        (existing, replacement) -> existing + ", " + replacement));

        return buildResponse(ErrorType.INVALID_PARAMETER, errors, ex);
    }

    // 处理其他未捕获异常
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex) {
        log.error("Unexpected error occurred: {}", ex.getMessage(), ex);
        return buildResponse(ErrorType.SYSTEM_ERROR, null, ex);
    }

    private ResponseEntity<ErrorResponse> buildResponse(ErrorType errorType,
                                                        Map<String, ?> details,
                                                        Exception ex) {
        ErrorResponse response = new ErrorResponse(
                errorType.getCode(),
                errorType.getMessage(),
                errorType.getHttpStatus(),
                details,
                Instant.now()
        );

        return new ResponseEntity<>(response, errorType.getHttpStatus());
    }

    private void logError(BaseException ex) {
        if (ex.getErrorType().getHttpStatus().is5xxServerError()) {
            log.error("System error occurred: [{}] {}", ex.getErrorType().getCode(),
                    ex.getMessage(), ex);
        } else {
            log.warn("Business exception: [{}] {} - Context: {}",
                    ex.getErrorType().getCode(), ex.getMessage(), ex.getContext());
        }
    }
} 