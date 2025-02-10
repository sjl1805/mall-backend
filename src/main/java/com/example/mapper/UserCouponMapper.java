package com.example.mapper;

import com.baomidou.mybatisplus.core.handlers.MybatisEnumTypeHandler;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.model.dto.coupon.CouponPageQueryDTO;
import com.example.model.entity.UserCoupon;
import org.apache.ibatis.annotations.*;


import java.util.List;

/**
 * @author 31815
 * @description 针对表【user_coupon(用户优惠券表)】的数据库操作Mapper
 * @createDate 2025-02-10 02:08:27
 * @Entity gg.model.UserCoupon
 */
public interface UserCouponMapper extends BaseMapper<UserCoupon> {

    /**
     * 分页查询用户优惠券
     */
    @Results(id = "userCouponMap", value = {
            @Result(property = "userCouponId", column = "user_coupon_id", id = true),
            @Result(property = "couponName", column = "coupon_name"),
            @Result(property = "getTime", column = "get_time"),
            @Result(property = "expireTime", column = "expire_time"),
            @Result(property = "statusDesc", column = "status", typeHandler = MybatisEnumTypeHandler.class)
    })
    @Select("<script>" +
            "SELECT uc.id AS user_coupon_id, c.name AS coupon_name, " +
            "CONCAT('满', c.min_amount, '减', c.value) AS discount_desc, " +
            "uc.get_time, uc.expire_time, uc.status AS status_desc " +
            "FROM user_coupon uc " +
            "JOIN coupon c ON uc.coupon_id = c.id " +
            "WHERE uc.user_id = #{userId} " +
            "<if test='query.status != null'>AND uc.status = #{query.status}</if>" +
            "<if test='query.expired != null'>" +
            "   <if test='query.expired'>AND uc.expire_time &lt; NOW()</if>" +
            "   <if test='!query.expired'>AND uc.expire_time &gt;= NOW()</if>" +
            "</if>" +
            "ORDER BY uc.get_time DESC" +
            "</script>")
    @Options(useCache = true, flushCache = Options.FlushCachePolicy.FALSE)
    IPage<CouponPageQueryDTO> selectUserCoupons(@Param("query") CouponPageQueryDTO query,
                                         @Param("userId") Long userId
    );


    /**
     * 批量标记优惠券为已使用
     */
    @Update("<script>" +
            "UPDATE user_coupon SET status = 'USED', use_time = NOW() " +
            "WHERE id IN " +
            "<foreach collection='couponIds' item='id' open='(' separator=',' close=')'>" +
            "#{id}" +
            "</foreach>" +
            "AND user_id = #{userId} " +
            "AND status = 'UNUSED'" +
            "</script>")
    @Options(timeout = 30)
    int batchMarkAsUsed(@Param("couponIds") List<Long> couponIds, @Param("userId") Long userId);

    /**
     * 统计用户未使用优惠券数量
     */
    @Select("SELECT COUNT(*) FROM user_coupon " +
            "WHERE user_id = #{userId} " +
            "AND status = 'UNUSED' " +
            "AND expire_time &gt;= NOW()")
    Integer countUnusedCoupons(@Param("userId") Long userId);

    /**
     * 获取即将过期的用户优惠券
     */
    @Select("SELECT * FROM user_coupon " +
            "WHERE expire_time BETWEEN NOW() AND DATE_ADD(NOW(), INTERVAL 3 DAY) " +
            "AND status = 'UNUSED'")
    List<UserCoupon> selectExpiringUserCoupons();

    /**
     * 批量失效过期优惠券
     */
    @Update("UPDATE user_coupon SET status = 'EXPIRED' " +
            "WHERE expire_time &lt; NOW() " +
            "AND status = 'UNUSED'")
    int batchExpireUserCoupons();

    /**
     * 检查优惠券是否可用
     */
    @Select("SELECT COUNT(*) FROM user_coupon " +
            "WHERE id = #{userCouponId} " +
            "AND user_id = #{userId} " +
            "AND status = 'UNUSED' " +
            "AND expire_time &gt;= NOW()")
    int checkCouponAvailability(@Param("userCouponId") Long userCouponId,
                                @Param("userId") Long userId);
}





