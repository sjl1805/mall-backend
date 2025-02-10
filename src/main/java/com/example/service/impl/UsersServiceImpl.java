package com.example.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.exception.core.BaseException;
import com.example.exception.ErrorType;
import com.example.mapper.UsersMapper;
import com.example.model.dto.auth.UserRegisterDTO;
import com.example.model.dto.user.UserUpdateDTO;
import com.example.model.entity.Users;
import com.example.model.enums.UserStatusEnum;

import com.example.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 31815
 * @description 针对表【users(用户表)】的数据库操作Service实现
 * @createDate 2025-02-10 02:08:11
 */
@Service
@RequiredArgsConstructor
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users>
        implements UsersService {

    private final PasswordEncoder passwordEncoder;
    private final UsersMapper usersMapper;

    @Override
    @Transactional
    public Long register(UserRegisterDTO dto) {
        // 检查用户名唯一性
        if (lambdaQuery().eq(Users::getUsername, dto.getUsername()).exists()) {
            throw new BaseException(ErrorType.USERNAME_EXISTS)
                .with("username", dto.getUsername());
        }

        Users user = new Users()
            .setUsername(dto.getUsername())
            .setPasswordHash(passwordEncoder.encode(dto.getPassword()))
            .setEmail(dto.getEmail())
            .setPhone(dto.getPhone());

        save(user);
        return user.getId();
    }

    @Override
    public Users authenticate(String loginId, String password) {
        Users user = usersMapper.selectByLoginId(loginId)
            .orElseThrow(() -> new BaseException(ErrorType.USER_NOT_FOUND));

        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            throw new BaseException(ErrorType.INVALID_CREDENTIALS);
        }

        if (!user.getStatus().isActive()) {
            throw new BaseException(ErrorType.USER_DISABLED);
        }

        return user;
    }

    @Override
    @Transactional
    public void updateUserInfo(Long userId, UserUpdateDTO dto) {
        update(Wrappers.<Users>lambdaUpdate()
            .eq(Users::getId, userId)
            .set(Users::getNickname, dto.getNickname())
            .set(Users::getAvatar, dto.getAvatar())
            .set(Users::getGender, dto.getGender()));
    }

    @Override
    @Transactional
    public void changePassword(Long userId, String oldPassword, String newPassword) {
        Users user = getById(userId);
        if (!passwordEncoder.matches(oldPassword, user.getPasswordHash())) {
            throw new BaseException(ErrorType.INVALID_PASSWORD);
        }
        
        update(Wrappers.<Users>lambdaUpdate()
            .eq(Users::getId, userId)
            .set(Users::getPasswordHash, passwordEncoder.encode(newPassword)));
    }

    @Override
    @Transactional
    public void toggleUserStatus(Long userId, boolean enabled) {
        UserStatusEnum status = enabled ? UserStatusEnum.ENABLED : UserStatusEnum.DISABLED;
        usersMapper.updateStatusAndRole(userId, status, null);
    }
}




