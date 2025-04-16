package com.example.common;

/**
 * 返回状态码常量类
 */
public class ResultCode {
    /**
     * 成功状态码
     */
    public static final int SUCCESS = 200;

    /**
     * 参数错误状态码
     */
    public static final int PARAM_ERROR = 400;

    /**
     * 未授权状态码
     */
    public static final int UNAUTHORIZED = 401;

    /**
     * 禁止访问状态码
     */
    public static final int FORBIDDEN = 403;

    /**
     * 资源不存在状态码
     */
    public static final int NOT_FOUND = 404;

    /**
     * 请求方法不允许状态码
     */
    public static final int METHOD_NOT_ALLOWED = 405;

    /**
     * 系统错误状态码
     */
    public static final int SYSTEM_ERROR = 500;

    /**
     * 业务错误状态码
     */
    public static final int BUSINESS_ERROR = 501;
} 