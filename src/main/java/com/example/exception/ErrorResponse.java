package com.example.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.Map;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private int code;
    private String message;
    private HttpStatus status;
    private Map<String, ?> details;
    private Instant timestamp;
} 