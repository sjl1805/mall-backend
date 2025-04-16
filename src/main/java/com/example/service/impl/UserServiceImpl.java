package com.example.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.ResultCode;
import com.example.exception.BusinessException;
import com.example.mapper.UserMapper;
import com.example.model.dto.LoginDTO;
import com.example.model.dto.RegisterDTO;
import com.example.model.dto.UserLoginVO;
import com.example.model.dto.UserQueryDTO;
import com.example.model.entity.User;
import com.example.service.UserService;
import com.example.util.CaptchaUtil;
import com.example.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 28619
 * @description 针对表【user(用户表)】的数据库操作Service实现
 * @createDate 2025-04-09 17:42:43
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    private final CaptchaUtil captchaUtil;
    private final JwtUtil jwtUtil;

    @Override
    public UserLoginVO login(LoginDTO loginDTO) {
        // 1. 验证验证码
        boolean captchaValid = captchaUtil.verifyCaptcha(loginDTO.getCaptchaKey(), loginDTO.getCaptcha());
        if (!captchaValid) {
            throw new BusinessException("验证码错误", ResultCode.PARAM_ERROR);
        }

        // 2. 查询用户
        User user = getByUsername(loginDTO.getUsername());
        if (user == null) {
            throw new BusinessException("用户名或密码错误", ResultCode.PARAM_ERROR);
        }

        // 3. 检查用户状态
        if (user.getStatus() == 0) {
            throw new BusinessException("账号已被禁用", ResultCode.FORBIDDEN);
        }

        // 4. 验证密码 - 使用MD5验证
        String encryptedPassword = MD5.create().digestHex(loginDTO.getPassword());
        boolean passwordMatch = user.getPassword().equals(encryptedPassword);
        if (!passwordMatch) {
            throw new BusinessException("用户名或密码错误", ResultCode.PARAM_ERROR);
        }

        // 5. 更新最后登录时间
        user.setLastLoginTime(new Date());
        updateById(user);

        // 6. 生成Token
        String token = jwtUtil.generateToken(user.getId(), user.getRole());

        // 7. 返回登录结果
        return UserLoginVO.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .nickname(user.getNickname())
                .avatar(user.getAvatar())
                .phone(user.getPhone())
                .lastLoginTime(user.getLastLoginTime())
                .registerTime(user.getCreateTime())
                .email(user.getEmail())
                .gender(user.getGender())
                .role(user.getRole())
                .token(token)
                .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserLoginVO register(RegisterDTO registerDTO) {
        // 1. 验证验证码
        boolean captchaValid = captchaUtil.verifyCaptcha(registerDTO.getCaptchaKey(), registerDTO.getCaptcha());
        if (!captchaValid) {
            throw new BusinessException("验证码错误", ResultCode.PARAM_ERROR);
        }

        // 2. 检查密码和确认密码是否一致
        if (!registerDTO.getPassword().equals(registerDTO.getConfirmPassword())) {
            throw new BusinessException("两次输入的密码不一致", ResultCode.PARAM_ERROR);
        }

        // 3. 检查用户名是否已存在
        if (existsByUsername(registerDTO.getUsername())) {
            throw new BusinessException("用户名已存在", ResultCode.PARAM_ERROR);
        }

        // 4. 创建用户
        User user = new User();
        user.setUsername(registerDTO.getUsername());
        // 使用MD5加密密码
        user.setPassword(MD5.create().digestHex(registerDTO.getPassword()));
        user.setNickname(registerDTO.getNickname() != null ? registerDTO.getNickname() : registerDTO.getUsername());
        user.setPhone(registerDTO.getPhone());
        user.setEmail(registerDTO.getEmail());
        user.setGender(0); // 默认未知
        user.setStatus(1); // 默认正常
        user.setRole(2);   // 默认普通用户
        user.setLastLoginTime(new Date()); // 设置注册时间为首次登录时间

        // 5. 保存用户
        save(user);

        // 6. 生成Token
        String token = jwtUtil.generateToken(user.getId(), user.getRole());

        // 7. 返回用户信息和token
        return UserLoginVO.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .nickname(user.getNickname())
                .avatar(user.getAvatar())
                .lastLoginTime(user.getLastLoginTime())
                .registerTime(user.getCreateTime())
                .phone(user.getPhone())
                .email(user.getEmail())
                .gender(user.getGender())
                .role(user.getRole())
                .token(token)
                .build();
    }

    @Override
    public User getByUsername(String username) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, username);
        return getOne(queryWrapper);
    }

    @Override
    public boolean existsByUsername(String username) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, username);
        return count(queryWrapper) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updatePassword(Long userId, String oldPassword, String newPassword) {
        // 1. 获取用户信息
        User user = getById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在", ResultCode.PARAM_ERROR);
        }

        // 2. 验证旧密码 - 使用MD5验证
        String encryptedOldPassword = MD5.create().digestHex(oldPassword);
        boolean passwordMatch = user.getPassword().equals(encryptedOldPassword);
        if (!passwordMatch) {
            throw new BusinessException("原密码错误", ResultCode.PARAM_ERROR);
        }

        // 3. 更新密码 - 使用MD5加密
        User updateUser = new User();
        updateUser.setId(userId);
        updateUser.setPassword(MD5.create().digestHex(newPassword));

        return updateById(updateUser);
    }

    @Override
    public Page<User> getUserPage(long page, long size, UserQueryDTO queryDTO) {
        Page<User> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();

        // 构建查询条件
        if (queryDTO != null) {
            // 用户名模糊查询
            if (StrUtil.isNotBlank(queryDTO.getUsername())) {
                queryWrapper.like(User::getUsername, queryDTO.getUsername());
            }

            // 手机号模糊查询
            if (StrUtil.isNotBlank(queryDTO.getPhone())) {
                queryWrapper.like(User::getPhone, queryDTO.getPhone());
            }

            // 邮箱模糊查询
            if (StrUtil.isNotBlank(queryDTO.getEmail())) {
                queryWrapper.like(User::getEmail, queryDTO.getEmail());
            }

            // 状态查询
            if (queryDTO.getStatus() != null) {
                queryWrapper.eq(User::getStatus, queryDTO.getStatus());
            }

            // 角色查询
            if (queryDTO.getRole() != null) {
                queryWrapper.eq(User::getRole, queryDTO.getRole());
            }

            // 注册时间范围查询
            if (queryDTO.getStartTime() != null) {
                queryWrapper.ge(User::getCreateTime, queryDTO.getStartTime());
            }

            if (queryDTO.getEndTime() != null) {
                queryWrapper.le(User::getCreateTime, queryDTO.getEndTime());
            }
        }

        // 设置排序
        queryWrapper.orderByDesc(User::getCreateTime);

        // 查询并返回分页结果
        return page(pageParam, queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addUser(User user) {
        // 1. 检查用户名是否已存在
        if (existsByUsername(user.getUsername())) {
            throw new BusinessException("用户名已存在", ResultCode.PARAM_ERROR);
        }

        // 2. 设置默认值
        if (user.getStatus() == null) {
            user.setStatus(1); // 默认正常状态
        }

        if (user.getRole() == null) {
            user.setRole(2); // 默认普通用户
        }

        if (user.getGender() == null) {
            user.setGender(0); // 默认未知
        }

        // 3. 加密密码 - 使用MD5加密
        user.setPassword(MD5.create().digestHex(user.getPassword()));
        user.setLastLoginTime(new Date()); // 设置为当前时间

        // 4. 保存用户
        return save(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateUser(User user) {
        // 1. 检查用户是否存在
        User existUser = getById(user.getId());
        if (existUser == null) {
            throw new BusinessException("用户不存在", ResultCode.PARAM_ERROR);
        }

        // 2. 如果修改用户名，需要检查用户名是否被其他用户占用
        if (StrUtil.isNotBlank(user.getUsername()) && !user.getUsername().equals(existUser.getUsername())) {
            if (existsByUsername(user.getUsername())) {
                throw new BusinessException("用户名已存在", ResultCode.PARAM_ERROR);
            }
        }

        // 3. 如果提供了密码，需要加密 - 使用MD5加密
        if (StrUtil.isNotBlank(user.getPassword())) {
            user.setPassword(MD5.create().digestHex(user.getPassword()));
        } else {
            // 不更新密码
            user.setPassword(null);
        }

        // 4. 更新用户
        return updateById(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteUser(Long userId) {
        // 检查用户是否存在
        User user = getById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在", ResultCode.PARAM_ERROR);
        }

        // 逻辑删除用户
        return removeById(userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateUserStatus(Long userId, Integer status) {
        // 1. 检查参数
        if (status != 0 && status != 1) {
            throw new BusinessException("状态值无效", ResultCode.PARAM_ERROR);
        }

        // 2. 检查用户是否存在
        User user = getById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在", ResultCode.PARAM_ERROR);
        }

        // 3. 更新用户状态
        User updateUser = new User();
        updateUser.setId(userId);
        updateUser.setStatus(status);

        return updateById(updateUser);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean resetPassword(Long userId, String newPassword) {
        // 1. 检查用户是否存在
        User user = getById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在", ResultCode.PARAM_ERROR);
        }

        // 2. 更新密码 - 使用MD5加密
        User updateUser = new User();
        updateUser.setId(userId);
        updateUser.setPassword(MD5.create().digestHex(newPassword));

        return updateById(updateUser);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateUserRole(Long userId, Integer role) {
        // 1. 检查参数
        if (role != 1 && role != 2) {
            throw new BusinessException("角色值无效", ResultCode.PARAM_ERROR);
        }

        // 2. 检查用户是否存在
        User user = getById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在", ResultCode.PARAM_ERROR);
        }

        // 3. 更新用户角色
        User updateUser = new User();
        updateUser.setId(userId);
        updateUser.setRole(role);

        return updateById(updateUser);
    }

    @Override
    public Map<String, Object> getUserStatistics() {
        // 统计用户数据
        Map<String, Object> statisticsMap = new HashMap<>();

        // 1. 总用户数
        long totalUsers = count();
        statisticsMap.put("totalUsers", totalUsers);

        // 2. 今日新增用户数
        long todayNewUsers = countTodayNewUsers();
        statisticsMap.put("todayNewUsers", todayNewUsers);

        // 3. 本周新增用户数
        long weekNewUsers = lambdaQuery()
                .ge(User::getCreateTime, DateUtil.beginOfWeek(new Date()))
                .lt(User::getCreateTime, DateUtil.endOfWeek(new Date()))
                .count();
        statisticsMap.put("weekNewUsers", weekNewUsers);

        // 4. 本月新增用户数
        long monthNewUsers = lambdaQuery()
                .ge(User::getCreateTime, DateUtil.beginOfMonth(new Date()))
                .lt(User::getCreateTime, DateUtil.endOfMonth(new Date()))
                .count();
        statisticsMap.put("monthNewUsers", monthNewUsers);

        // 5. 性别分布
        Map<Integer, Long> genderDistribution = getGenderDistribution();
        statisticsMap.put("genderDistribution", genderDistribution);

        // 6. 最近一周用户注册数据
        Map<String, Object> dailyRegistrations = new HashMap<>();
        for (int i = 6; i >= 0; i--) {
            Date date = DateUtil.offsetDay(new Date(), -i);
            String dayStr = DateUtil.format(date, "yyyy-MM-dd");

            long dayCount = lambdaQuery()
                    .ge(User::getCreateTime, DateUtil.beginOfDay(date))
                    .lt(User::getCreateTime, DateUtil.endOfDay(date))
                    .count();

            dailyRegistrations.put(dayStr, dayCount);
        }
        statisticsMap.put("dailyRegistrations", dailyRegistrations);

        return statisticsMap;
    }

    @Override
    public long countTodayNewUsers() {
        // 统计今日新增用户数
        return lambdaQuery()
                .ge(User::getCreateTime, DateUtil.beginOfDay(new Date()))
                .lt(User::getCreateTime, DateUtil.endOfDay(new Date()))
                .count();
    }

    @Override
    public long countActiveUsers(int days) {
        // 统计近N天内有登录记录的用户数
        Date startDate = DateUtil.offsetDay(new Date(), -days);
        return lambdaQuery()
                .ge(User::getLastLoginTime, startDate)
                .count();
    }

    @Override
    public Map<Integer, Long> getGenderDistribution() {
        // 统计用户性别分布
        Map<Integer, Long> distribution = new HashMap<>();

        // 0-未知
        long unknownCount = lambdaQuery()
                .eq(User::getGender, 0)
                .count();
        distribution.put(0, unknownCount);

        // 1-男
        long maleCount = lambdaQuery()
                .eq(User::getGender, 1)
                .count();
        distribution.put(1, maleCount);

        // 2-女
        long femaleCount = lambdaQuery()
                .eq(User::getGender, 2)
                .count();
        distribution.put(2, femaleCount);

        return distribution;
    }
}




