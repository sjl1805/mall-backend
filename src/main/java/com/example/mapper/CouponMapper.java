package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.model.dto.coupon.CouponPageQueryDTO;
import com.example.model.dto.coupon.UserCouponDTO;
import com.example.model.entity.Coupon;
import com.example.model.enums.CouponStatusEnum;
import org.apache.ibatis.annotations.*;


import java.util.List;

/**
 * @author 31815
 * @description 针对表【coupon(优惠券表)】的数据库操作Mapper
 * @createDate 2025-02-10 02:09:21
 * @Entity gg.model.Coupon
 */
public interface CouponMapper extends BaseMapper<Coupon> {


    @Results(id = "adminCouponMap", value = {
            @Result(property = "totalIssued", column = "total_issued"),
            @Result(property = "usedCount", column = "used_count"),
            @Result(property = "startTime", column = "start_time"),
            @Result(property = "endTime", column = "end_time")
    })
    @Select("<script>" +
            "SELECT c.*, COUNT(uc.id) AS total_issued, " +
            "SUM(CASE WHEN uc.status = 'USED' THEN 1 ELSE 0 END) AS used_count " +
            "FROM coupon c " +
            "LEFT JOIN user_coupon uc ON c.id = uc.coupon_id " +
            "<where>" +
            "   <if test='query.couponName != null'>AND c.name LIKE CONCAT('%',#{query.couponName},'%')</if>" +
            "   <if test='query.status != null'>AND c.status = #{query.status}</if>" +
            "   <if test='query.expired != null'>" +
            "       <if test='query.expired'>AND c.end_time &lt; NOW()</if>" +
            "       <if test='!query.expired'>AND c.end_time &gt;= NOW()</if>" +
            "   </if>" +
            "</where>" +
            "GROUP BY c.id " +
            "ORDER BY c.create_time DESC" +
            "</script>")
    IPage<CouponPageQueryDTO> selectAdminCouponList(@Param("query") CouponPageQueryDTO query);



    @Update("UPDATE coupon SET status = #{status} " +
            "WHERE id = #{couponId} " +
            "AND merchant_id = #{merchantId} " +
            "AND start_time &lt;= NOW() " +
            "AND end_time &gt;= NOW()")
    int updateStatusWithTimeCheck(@Param("couponId") Long couponId,
                                  @Param("status") CouponStatusEnum status,
                                  @Param("merchantId") Long merchantId);

    @Select("SELECT * FROM coupon " +
            "WHERE end_time BETWEEN NOW() AND DATE_ADD(NOW(), INTERVAL 3 DAY) " +
            "AND status = 'VALID'")
    @Options(useCache = true)
    List<Coupon> selectExpiringCoupons();

    @Select("SELECT COUNT(*) FROM user_coupon " +
            "WHERE user_id = #{userId} " +
            "AND status = 'UNUSED' " +
            "AND expire_time &gt;= NOW()")
    @Options(useCache = true, flushCache = Options.FlushCachePolicy.FALSE)
    Integer countAvailableCoupons(@Param("userId") Long userId);

    @Update("UPDATE coupon SET status = 'EXPIRED' " +
            "WHERE end_time &lt; NOW() " +
            "AND status IN ('VALID', 'PAUSED')")
    @Options(flushCache = Options.FlushCachePolicy.TRUE)
    int batchExpireCoupons();

    @Results(id = "userCouponMap", value = {
            @Result(property = "userCouponId", column = "user_coupon_id"),
            @Result(property = "couponName", column = "coupon_name"),
            @Result(property = "discountDesc", column = "discount_desc"),
            @Result(property = "getTime", column = "get_time"),
            @Result(property = "expireTime", column = "expire_time"),
            @Result(property = "statusDesc", column = "status_desc")
    })
    @Select("SELECT uc.id AS user_coupon_id, c.name AS coupon_name, " +
            "CONCAT('满', c.min_amount, '减', c.value) AS discount_desc, " +
            "uc.get_time, uc.expire_time, uc.status AS status_desc " +
            "FROM user_coupon uc " +
            "JOIN coupon c ON uc.coupon_id = c.id " +
            "WHERE uc.user_id = #{userId} " +
            "AND uc.status = 'UNUSED' " +
            "AND uc.expire_time &gt;= NOW() " +
            "ORDER BY c.min_amount DESC")
    List<UserCouponDTO> selectAvailableCoupons(@Param("userId") Long userId);
}




