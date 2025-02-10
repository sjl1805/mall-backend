package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.model.entity.UserAddress;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 31815
 * @description 针对表【user_address(用户收货地址表)】的数据库操作Mapper
 * @createDate 2025-02-10 02:08:40
 * @Entity gg.model.UserAddress
 */
@Mapper
public interface UserAddressMapper extends BaseMapper<UserAddress> {

}





