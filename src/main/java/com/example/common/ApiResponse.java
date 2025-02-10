package com.example.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "统一响应格式")
public class ApiResponse<T> {
    @Schema(name = "code", description = "状态码", example = "200")
    private int code;
    
    @Schema(name = "message", description = "提示信息", example = "操作成功")
    private String message;
    
    @Schema(name = "data", description = "响应数据")
    private T data;
    
    @Schema(name = "timestamp", description = "时间戳", example = "1710921600000")
    private long timestamp = System.currentTimeMillis();

    // 成功响应快捷方法
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(ResultCode.SUCCESS, data);
    }

    // 失败响应快捷方法
    public static <T> ApiResponse<T> fail(ResultCode resultCode) {
        return new ApiResponse<>(resultCode, null);
    }
    public static <T> ApiResponse<T> fail(int code, String message) {
        return new ApiResponse<>(code, message, null);
    } 

    public ApiResponse(ResultCode resultCode, T data) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.data = data;
    }

    public ApiResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
} 