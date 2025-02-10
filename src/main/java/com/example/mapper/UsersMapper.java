package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.model.entity.Users;
import com.example.model.dto.user.AdminUserDTO;
import com.example.model.enums.UserRoleEnum;

import com.example.model.enums.UserStatusEnum;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;


import java.util.List;

/**
 * @author 31815
 * @description 针对表【users(用户表)】的数据库操作Mapper
 * @createDate 2025-02-10 02:08:11
 * @Entity gg.model.Users
 */
@Mapper
public interface UsersMapper extends BaseMapper<Users> {

    /**
     * 根据登录标识（用户名/手机/邮箱）查询用户
     */
    @Select("SELECT * FROM users WHERE username = #{loginId} OR phone = #{loginId} OR email = #{loginId}")
    Users selectByLoginId(@Param("loginId") String loginId);

    /**
     * 根据手机号查询用户
     */
    @Select("SELECT * FROM users WHERE phone = #{phone}")
    Users selectByPhone(@Param("phone") String phone);

    /**
     * 根据邮箱查询用户
     */
    @Select("SELECT * FROM users WHERE email = #{email}")
    Users selectByEmail(@Param("email") String email);

    /**
     * 分页查询用户列表（管理员用）
     */
    @Select("<script>" +
            "SELECT * FROM users " +
            "<where>" +
            "   <if test='role != null'>AND role = #{role}</if>" +
            "   <if test='username != null'>AND username LIKE CONCAT('%',#{username},'%')</if>" +
            "   <if test='phone != null'>AND phone LIKE CONCAT('%',#{phone},'%')</if>" +
            "</where>" +
            "ORDER BY create_time DESC" +
            "</script>")
    List<AdminUserDTO> selectAdminUserList(Page<AdminUserDTO> page,
                                          @Param("role") UserRoleEnum role,
                                          @Param("username") String username,
                                          @Param("phone") String phone);

    /**
     * 根据ID获取管理员视角用户详情
     */
    @Select("SELECT * FROM users WHERE id = #{userId}")
    AdminUserDTO selectAdminUserById(@Param("userId") Long userId);

    /**
     * 更新用户状态和角色（管理员操作）
     */
    @Update("UPDATE users SET status = #{status}, role = #{role} WHERE id = #{userId}")
    int updateUserStatusAndRole(@Param("userId") Long userId,
                               @Param("status") UserStatusEnum status,
                               @Param("role") UserRoleEnum role);

    /**
     * 更新密码（带版本号校验）
     */
    @Update("UPDATE users SET password_hash = #{newPassword}, salt = #{salt}, version = version + 1 " +
            "WHERE id = #{userId} AND version = #{version}")
    int updatePassword(@Param("userId") Long userId,
                      @Param("newPassword") String newPassword,
                      @Param("salt") String salt,
                      @Param("version") Integer version);
}





