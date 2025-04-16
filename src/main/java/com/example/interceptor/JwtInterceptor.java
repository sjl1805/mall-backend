package com.example.interceptor;

import com.example.common.ResultCode;
import com.example.exception.BusinessException;
import com.example.util.JwtUtil;
import com.example.util.UserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * JWT拦截器
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {

    /**
     * Authorization 请求头名称
     */
    private static final String AUTHORIZATION_HEADER = "Authorization";
    /**
     * Bearer 前缀
     */
    private static final String BEARER_PREFIX = "Bearer ";
    private final JwtUtil jwtUtil;

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
        // 获取请求头中的Token
        String token = getTokenFromRequest(request);

        // 如果没有Token，则直接拦截
        if (!StringUtils.hasText(token)) {
            throw new BusinessException("未提供授权令牌", ResultCode.UNAUTHORIZED);
        }

        // 验证Token
        if (!jwtUtil.validateToken(token)) {
            throw new BusinessException("授权令牌无效或已过期", ResultCode.UNAUTHORIZED);
        }

        // 从Token中获取用户ID和角色
        Long userId = jwtUtil.getUserIdFromToken(token);
        Integer role = jwtUtil.getUserRoleFromToken(token);

        // 将用户信息存入请求上下文
        UserUtil.setCurrentUser(userId, role);

        return true;
    }

    /**
     * 从请求中获取Token
     *
     * @param request HTTP请求
     * @return Token
     */
    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(BEARER_PREFIX.length());
        }
        return null;
    }
} 