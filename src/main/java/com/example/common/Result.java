package com.example.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "统一响应格式")
public class Result<T> {
    @Schema(name = "code", description = "状态码", example = "200")
    private int code;

    @Schema(name = "message", description = "提示信息", example = "操作成功")
    private String message;

    @Schema(name = "data", description = "响应数据")
    private T data;

    @Schema(name = "timestamp", description = "时间戳", example = "1710921600000")
    private long timestamp = System.currentTimeMillis();

    public Result(ResultCode resultCode, T data) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.data = data;
    }

    public Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    // 成功响应快捷方法
    public static <T> Result<T> success(T data) {
        return new Result<>(ResultCode.SUCCESS, data);
    }

    // 失败响应快捷方法
    public static <T> Result<T> error(ResultCode resultCode) {
        return new Result<>(resultCode, null);
    }


    public static <T> Result<T> error(int code, String message) {
        return new Result<>(code, message, null);
    }

} 