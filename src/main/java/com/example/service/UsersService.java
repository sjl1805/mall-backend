package com.example.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.model.dto.user.AdminUserDTO;
import com.example.model.dto.user.UserPageQueryDTO;
import com.example.model.entity.Users;
import com.example.model.enums.UserRoleEnum;
import com.example.model.enums.UserStatusEnum;

import java.util.Optional;


/**
 * @author 31815
 * @description 针对表【users(用户表)】的数据库操作Service
 * @createDate 2025-02-10 02:08:11
 */
public interface UsersService extends IService<Users> {


    Optional<Users> getUserByPhone(String phone);

    Optional<Users> getUserByEmail(String email);

    UserPageQueryDTO getAdminUserList(UserPageQueryDTO query);

    Optional<AdminUserDTO> getAdminUserById(Long userId);

    boolean updateUserStatusAndRole(Long userId, UserStatusEnum status, UserRoleEnum role);

    boolean updatePassword(Long userId, String newPassword, String salt, Integer version);

}
