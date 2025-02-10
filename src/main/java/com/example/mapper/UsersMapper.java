package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.model.entity.Users;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 31815
 * @description 针对表【users(用户表)】的数据库操作Mapper
 * @createDate 2025-02-10 02:08:11
 * @Entity gg.model.Users
 */
@Mapper
public interface UsersMapper extends BaseMapper<Users> {

}





