package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.model.dto.auth.UserRegisterDTO;
import com.example.model.dto.user.UserUpdateDTO;
import com.example.model.entity.Users;

/**
 * @author 31815
 * @description 针对表【users(用户表)】的数据库操作Service
 * @createDate 2025-02-10 02:08:11
 */
public interface UsersService extends IService<Users> {
    
    // 用户注册（返回用户ID）
    Long register(UserRegisterDTO registerDTO);
    
    // 用户登录认证
    Users authenticate(String loginId, String password);
    
    // 更新用户信息
    void updateUserInfo(Long userId, UserUpdateDTO updateDTO);
    
    // 修改密码（带旧密码验证）
    void changePassword(Long userId, String oldPassword, String newPassword);
    
    // 管理员禁用/启用用户
    void toggleUserStatus(Long userId, boolean enabled);
}
