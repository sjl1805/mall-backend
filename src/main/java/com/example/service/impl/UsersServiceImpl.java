package com.example.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mapper.UsersMapper;
import com.example.model.dto.user.AdminUserDTO;
import com.example.model.dto.user.UserPageQueryDTO;
import com.example.model.entity.Users;
import com.example.model.enums.UserRoleEnum;
import com.example.model.enums.UserStatusEnum;

import com.example.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author 31815
 * @description 针对表【users(用户表)】的数据库操作Service实现
 * @createDate 2025-02-10 02:08:11
 */
@Service
@RequiredArgsConstructor
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users>
        implements UsersService {

    private final UsersMapper usersMapper;

    @Override
    public Optional<Users> getUserByPhone(String phone) {
        return usersMapper.selectByPhone(phone);
    }

    @Override
    public Optional<Users> getUserByEmail(String email) {
        return usersMapper.selectByEmail(email);
    }

    @Override
    public IPage<UserPageQueryDTO> getAdminUserList(UserPageQueryDTO query) {
        return usersMapper.selectAdminUserList(query);
    }



    @Override
    public Optional<AdminUserDTO> getAdminUserById(Long userId) {
        return usersMapper.selectAdminUserById(userId);
    }

    @Override
    @Transactional
    public boolean updateUserStatusAndRole(Long userId, UserStatusEnum status, UserRoleEnum role) {
        return usersMapper.updateUserStatusAndRole(userId, status, role) > 0;
    }

    @Override
    @Transactional
    public boolean updatePassword(Long userId, String newPassword, String salt, Integer version) {
        return usersMapper.updatePassword(userId, newPassword, salt, version) > 0;
    }
}




