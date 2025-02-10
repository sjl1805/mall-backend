package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.model.dto.coupon.AdminCouponDTO;
import com.example.model.dto.coupon.CouponPageQueryDTO;
import com.example.model.dto.coupon.UserCouponDTO;
import com.example.model.entity.Coupon;
import com.example.model.enums.CouponStatusEnum;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 31815
 * @description 针对表【coupon(优惠券表)】的数据库操作Mapper
 * @createDate 2025-02-10 02:09:21
 * @Entity gg.model.Coupon
 */
@Mapper
public interface CouponMapper extends BaseMapper<Coupon> {

    /**
     * 分页查询优惠券（管理员用）
     */
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
    List<AdminCouponDTO> selectAdminCouponList(Page<AdminCouponDTO> page, @Param("query") CouponPageQueryDTO query);

    /**
     * 更新优惠券状态（带时间校验）
     */
    @Update("UPDATE coupon SET status = #{status} " +
            "WHERE id = #{couponId} " +
            "AND start_time &lt;= NOW() " +
            "AND end_time &gt;= NOW()")
    int updateStatusWithTimeCheck(@Param("couponId") Long couponId, 
                                @Param("status") CouponStatusEnum status);

    /**
     * 获取即将过期的优惠券
     */
    @Select("SELECT * FROM coupon " +
            "WHERE end_time BETWEEN NOW() AND DATE_ADD(NOW(), INTERVAL 3 DAY) " +
            "AND status = 'VALID'")
    List<Coupon> selectExpiringCoupons();

    /**
     * 统计用户可用优惠券数量
     */
    @Select("SELECT COUNT(*) FROM user_coupon " +
            "WHERE user_id = #{userId} " +
            "AND status = 'UNUSED' " +
            "AND expire_time &gt;= NOW()")
    Integer countAvailableCoupons(@Param("userId") Long userId);

    /**
     * 批量失效过期优惠券
     */
    @Update("UPDATE coupon SET status = 'EXPIRED' " +
            "WHERE end_time &lt; NOW() " +
            "AND status IN ('VALID', 'PAUSED')")
    int batchExpireCoupons();

    /**
     * 获取用户可用的优惠券列表
     */
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




