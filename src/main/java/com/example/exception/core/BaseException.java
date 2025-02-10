package com.example.exception.core;

import com.example.exception.ErrorType;

import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class BaseException extends RuntimeException {
    private final ErrorType errorType;
    private final Map<String, Object> context = new HashMap<>();
    private final Instant timestamp = Instant.now();

    public BaseException(ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
    }

    // 上下文信息添加方法
    public BaseException with(String key, Object value) {
        context.put(key, value);
        return this;
    }

    // Getters
    public ErrorType getErrorType() {
        return errorType;
    }

    public Map<String, Object> getContext() {
        return Collections.unmodifiableMap(context);
    }

    public Instant getTimestamp() {
        return timestamp;
    }
} 