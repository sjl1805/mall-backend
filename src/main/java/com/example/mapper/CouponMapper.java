package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.model.entity.Coupon;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 31815
 * @description 针对表【coupon(优惠券表)】的数据库操作Mapper
 * @createDate 2025-02-10 02:09:21
 * @Entity gg.model.Coupon
 */
@Mapper
public interface CouponMapper extends BaseMapper<Coupon> {

}




