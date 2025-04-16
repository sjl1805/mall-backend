package com.example.util;

import com.example.common.ResultCode;
import com.example.exception.BusinessException;
import com.example.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * 用户工具类
 */
@Getter
@Component
@RequiredArgsConstructor
public class UserUtil {

    /**
     * 当前用户ID的属性名
     */
    public static final String CURRENT_USER_ID = "CURRENT_USER_ID";
    /**
     * 当前用户角色的属性名
     */
    public static final String CURRENT_USER_ROLE = "CURRENT_USER_ROLE";
    /**
     * 管理员角色值
     */
    public static final Integer ROLE_ADMIN = 1;
    private final UserService userService;

    /**
     * 设置当前用户ID和角色
     *
     * @param userId 用户ID
     * @param role   用户角色
     */
    public static void setCurrentUser(Long userId, Integer role) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            requestAttributes.setAttribute(CURRENT_USER_ID, userId, RequestAttributes.SCOPE_REQUEST);
            requestAttributes.setAttribute(CURRENT_USER_ROLE, role, RequestAttributes.SCOPE_REQUEST);
        }
    }

    /**
     * 获取当前登录的用户ID
     *
     * @return 用户ID
     */
    public Long getCurrentUserId() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            throw new BusinessException("获取当前用户ID失败", ResultCode.UNAUTHORIZED);
        }

        Object userId = requestAttributes.getAttribute(CURRENT_USER_ID, RequestAttributes.SCOPE_REQUEST);
        if (userId == null) {
            throw new BusinessException("用户未登录", ResultCode.UNAUTHORIZED);
        }

        return (Long) userId;
    }

    /**
     * 获取当前登录的用户角色
     *
     * @return 用户角色
     */
    public Integer getCurrentUserRole() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            throw new BusinessException("获取当前用户角色失败", ResultCode.UNAUTHORIZED);
        }

        Object userRole = requestAttributes.getAttribute(CURRENT_USER_ROLE, RequestAttributes.SCOPE_REQUEST);
        if (userRole == null) {
            throw new BusinessException("用户未登录", ResultCode.UNAUTHORIZED);
        }

        return (Integer) userRole;
    }

    /**
     * 检查当前用户是否为管理员
     *
     * @return 是否为管理员
     */
    public boolean isAdmin() {
        Integer role = getCurrentUserRole();
        return ROLE_ADMIN.equals(role);
    }

    /**
     * 获取当前登录的用户ID，未登录则返回null
     *
     * @return 用户ID，未登录时返回null
     */
    public Long getCurrentUserIdOrNull() {
        try {
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            if (requestAttributes == null) {
                return null;
            }

            Object userId = requestAttributes.getAttribute(CURRENT_USER_ID, RequestAttributes.SCOPE_REQUEST);
            return userId != null ? (Long) userId : null;
        } catch (Exception e) {
            // 出现异常时返回null
            return null;
        }
    }

}