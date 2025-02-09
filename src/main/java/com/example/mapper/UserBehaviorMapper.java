package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.model.entity.UserBehavior;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 31815
 * @description 针对表【user_behavior(用户行为记录表)】的数据库操作Mapper
 * @createDate 2025-02-10 02:08:35
 * @Entity gg.model.UserBehavior
 */
@Mapper
public interface UserBehaviorMapper extends BaseMapper<UserBehavior> {

}





