package com.example.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.model.dto.LoginDTO;
import com.example.model.dto.RegisterDTO;
import com.example.model.dto.UserLoginVO;
import com.example.model.dto.UserQueryDTO;
import com.example.model.entity.User;

import java.util.Map;

/**
 * @author 28619
 * @description 针对表【user(用户表)】的数据库操作Service
 * @createDate 2025-04-09 17:42:43
 */
public interface UserService extends IService<User> {
    /**
     * 用户登录
     *
     * @param loginDTO 登录参数
     * @return 登录结果
     */
    UserLoginVO login(LoginDTO loginDTO);

    /**
     * 用户注册
     *
     * @param registerDTO 注册参数
     * @return 注册成功的用户信息和token
     */
    UserLoginVO register(RegisterDTO registerDTO);

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return 用户对象
     */
    User getByUsername(String username);

    /**
     * 检查用户名是否已存在
     *
     * @param username 用户名
     * @return 是否存在
     */
    boolean existsByUsername(String username);

    /**
     * 修改密码
     *
     * @param userId      用户ID
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return 是否成功
     */
    boolean updatePassword(Long userId, String oldPassword, String newPassword);

    /**
     * 管理员获取用户分页列表
     *
     * @param page     页码
     * @param size     每页数量
     * @param queryDTO 查询条件
     * @return 用户分页数据
     */
    Page<User> getUserPage(long page, long size, UserQueryDTO queryDTO);

    /**
     * 管理员添加用户
     *
     * @param user 用户信息
     * @return 是否成功
     */
    boolean addUser(User user);

    /**
     * 管理员更新用户信息
     *
     * @param user 用户信息
     * @return 是否成功
     */
    boolean updateUser(User user);

    /**
     * 管理员删除用户
     *
     * @param userId 用户ID
     * @return 是否成功
     */
    boolean deleteUser(Long userId);

    /**
     * 管理员禁用/启用用户
     *
     * @param userId 用户ID
     * @param status 状态：0-禁用，1-正常
     * @return 是否成功
     */
    boolean updateUserStatus(Long userId, Integer status);

    /**
     * 管理员重置用户密码
     *
     * @param userId      用户ID
     * @param newPassword 新密码
     * @return 是否成功
     */
    boolean resetPassword(Long userId, String newPassword);

    /**
     * 管理员修改用户角色
     *
     * @param userId 用户ID
     * @param role   角色：1-管理员，2-用户
     * @return 是否成功
     */
    boolean updateUserRole(Long userId, Integer role);

    /**
     * 获取用户数量统计信息
     *
     * @return 用户统计信息，包括总用户数、今日新增用户数、本周新增用户数、本月新增用户数
     */
    Map<String, Object> getUserStatistics();

    /**
     * 统计今日新增用户数
     *
     * @return 今日新增用户数
     */
    long countTodayNewUsers();

    /**
     * 统计活跃用户数（n天内有登录记录）
     *
     * @param days 天数
     * @return 活跃用户数
     */
    long countActiveUsers(int days);

    /**
     * 获取用户性别分布
     *
     * @return 性别分布数据，key为性别代码，value为用户数量
     */
    Map<Integer, Long> getGenderDistribution();
}
