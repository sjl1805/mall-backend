package com.example.controller;

import com.example.annotation.RequiresRole;
import com.example.common.Result;
import com.example.util.UserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 角色控制器
 * 提供角色相关的接口
 */
@Slf4j
@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
public class RoleController {

    private final UserUtil userUtil;

    /**
     * 获取当前用户的角色信息
     *
     * @return 角色信息
     */
    @GetMapping("/info")
    public Result<Map<String, Object>> getRoleInfo() {
        Integer role = userUtil.getCurrentUserRole();
        String roleName = role == 1 ? "管理员" : "普通用户";

        Map<String, Object> roleInfo = new HashMap<>();
        roleInfo.put("role", role);
        roleInfo.put("roleName", roleName);
        roleInfo.put("permissions", getPermissionsByRole(role));

        return Result.success(roleInfo);
    }

    /**
     * 检查当前用户是否为管理员
     * 该接口需要管理员角色才能访问，用于前端判断是否有管理权限
     *
     * @return 检查结果
     */
    @GetMapping("/check-admin")
    @RequiresRole(1) // 要求管理员角色
    public Result<Boolean> checkAdmin() {
        return Result.success(true, "当前用户具有管理员权限");
    }

    /**
     * 根据角色获取权限列表
     *
     * @param role 角色
     * @return 权限列表
     */
    private String[] getPermissionsByRole(Integer role) {
        if (role == 1) {
            // 管理员权限
            return new String[]{
                    "user:view", "user:add", "user:edit", "user:delete",
                    "product:view", "product:add", "product:edit", "product:delete",
                    "order:view", "order:process", "order:cancel",
                    "statistics:view",
                    "system:config"
            };
        } else {
            // 普通用户权限
            return new String[]{
                    "user:self:view", "user:self:edit",
                    "product:view",
                    "order:self:view", "order:self:add", "order:self:cancel"
            };
        }
    }
}