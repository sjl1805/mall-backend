package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.model.dto.order.AdminOrderDTO;
import com.example.model.dto.order.OrderPageQueryDTO;
import com.example.model.entity.Orders;
import com.example.model.enums.OrderStatusEnum;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

/**
 * @author 31815
 * @description 针对表【orders(订单表)】的数据库操作Mapper
 * @createDate 2025-02-10 02:09:11
 * @Entity gg.model.Orders
 */
@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {

    @Results(id = "adminOrderMap", value = {
            @Result(property = "orderNo", column = "order_no"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "payTime", column = "pay_time"),
            @Result(property = "deliveryTime", column = "delivery_time"),
            @Result(property = "receiveTime", column = "receive_time")
    })
    @Select("<script>" +
            "SELECT o.*, u.username " +
            "FROM orders o " +
            "LEFT JOIN users u ON o.user_id = u.id " +
            "<where>" +
            "   <if test='query.orderNo != null'>AND o.order_no LIKE CONCAT('%',#{query.orderNo},'%')</if>" +
            "   <if test='query.status != null'>AND o.status = #{query.status}</if>" +
            "   <if test='query.createTimeStart != null'>AND o.create_time &gt;= #{query.createTimeStart}</if>" +
            "   <if test='query.createTimeEnd != null'>AND o.create_time &lt;= #{query.createTimeEnd}</if>" +
            "   <if test='!query.includeCanceled'>AND o.status != 'CANCELED'</if>" +
            "</where>" +
            "ORDER BY o.create_time DESC" +
            "</script>")
    IPage<AdminOrderDTO> selectAdminOrderList(Page<AdminOrderDTO> page, @Param("query") OrderPageQueryDTO query);

    @Options(useCache = true, flushCache = Options.FlushCachePolicy.FALSE)
    @Select("SELECT o.*, u.username " +
            "FROM orders o " +
            "LEFT JOIN users u ON o.user_id = u.id " +
            "WHERE o.order_no = #{orderNo}")
    Optional<AdminOrderDTO> selectByOrderNo(@Param("orderNo") String orderNo);

    @Update("<script>" +
            "UPDATE orders SET status = #{status}, version = version + 1 " +
            "WHERE id = #{id} AND version = #{version} " +
            "AND user_id = #{userId}")
    int updateOrderStatus(@Param("id") Long id,
                          @Param("status") OrderStatusEnum status,
                          @Param("version") Integer version,
                          @Param("userId") Long userId);

    /**
     * 获取用户订单列表
     */
    @Select("SELECT * FROM orders WHERE user_id = #{userId} ORDER BY create_time DESC")
    List<Orders> selectUserOrders(@Param("userId") Long userId);
}





