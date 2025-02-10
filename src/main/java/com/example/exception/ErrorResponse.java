package com.example.exception;

import lombok.Data;
import lombok.AllArgsConstructor;

import java.time.Instant;
import java.util.Map;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private int code;
    private String message;
    private HttpStatus status;
    private Map<String, ?> details;
    private Instant timestamp;
} 