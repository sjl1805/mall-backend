package com.example.interceptor;

import com.example.annotation.RequiresRole;
import com.example.common.ResultCode;
import com.example.exception.BusinessException;
import com.example.util.UserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * 角色拦截器
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RoleInterceptor implements HandlerInterceptor {

    private final UserUtil userUtil;

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
        // 如果不是处理方法，则直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;

        // 获取方法上的注解
        RequiresRole methodAnnotation = handlerMethod.getMethod().getAnnotation(RequiresRole.class);
        // 获取类上的注解
        RequiresRole classAnnotation = handlerMethod.getBeanType().getAnnotation(RequiresRole.class);

        // 如果没有注解，则直接通过
        if (methodAnnotation == null && classAnnotation == null) {
            return true;
        }

        // 获取当前用户角色
        Integer userRole = userUtil.getCurrentUserRole();

        // 检查方法注解
        if (methodAnnotation != null && !hasRole(userRole, methodAnnotation.value())) {
            throw new BusinessException("您没有权限访问此接口", ResultCode.FORBIDDEN);
        }

        // 检查类注解
        if (classAnnotation != null && !hasRole(userRole, classAnnotation.value())) {
            throw new BusinessException("您没有权限访问此接口", ResultCode.FORBIDDEN);
        }

        return true;
    }

    /**
     * 检查用户是否拥有指定角色
     *
     * @param userRole      用户角色
     * @param requiredRoles 需要的角色
     * @return 是否拥有
     */
    private boolean hasRole(Integer userRole, int[] requiredRoles) {
        // 如果没有指定需要的角色，则任何角色都可访问
        if (requiredRoles.length == 0) {
            return true;
        }

        // 检查用户角色是否在需要的角色中
        return Arrays.stream(requiredRoles)
                .anyMatch(role -> role == userRole);
    }
} 