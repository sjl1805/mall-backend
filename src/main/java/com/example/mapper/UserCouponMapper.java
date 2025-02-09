package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.model.entity.UserCoupon;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 31815
 * @description 针对表【user_coupon(用户优惠券表)】的数据库操作Mapper
 * @createDate 2025-02-10 02:08:27
 * @Entity gg.model.UserCoupon
 */
@Mapper
public interface UserCouponMapper extends BaseMapper<UserCoupon> {

}





