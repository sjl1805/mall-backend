package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.model.dto.order.AdminOrderItemDTO;
import com.example.model.dto.order.OrderItemDTO;
import com.example.model.dto.order.OrderItemPageQueryDTO;
import com.example.model.entity.OrderItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author 31815
 * @description 针对表【order_item(订单商品表)】的数据库操作Mapper
 * @createDate 2025-02-10 02:09:15
 * @Entity gg.model.OrderItem
 */
@Mapper
public interface OrderItemMapper extends BaseMapper<OrderItem> {

    @Results(id = "orderItemMap", value = {
        @Result(property = "productName", column = "product_name"),
        @Result(property = "productImage", column = "product_image")
    })
    @Select("SELECT oi.*, p.name as product_name, " +
            "JSON_UNQUOTE(JSON_EXTRACT(p.images, '$[0]')) as product_image, " +
            "'' as sku_spec " +
            "FROM order_item oi " +
            "LEFT JOIN products p ON oi.product_id = p.id " +
            "WHERE oi.order_no = #{orderNo}")
    List<OrderItemDTO> selectByOrderNoWithProduct(@Param("orderNo") String orderNo);

    @Options(useCache = true)
    @Select("<script>" +
            "SELECT oi.*, p.name as product_name, u.username " +
            "FROM order_item oi " +
            "LEFT JOIN products p ON oi.product_id = p.id " +
            "LEFT JOIN users u ON oi.user_id = u.id " +
            "<where>" +
            "   <if test='query.orderNo != null'>AND oi.order_no = #{query.orderNo}</if>" +
            "   <if test='query.productName != null'>AND p.name LIKE CONCAT('%',#{query.productName},'%')</if>" +
            "   <if test='query.minPrice != null'>AND oi.price &gt;= #{query.minPrice}</if>" +
            "   <if test='query.maxPrice != null'>AND oi.price &lt;= #{query.maxPrice}</if>" +
            "</where>" +
            "ORDER BY oi.create_time DESC" +
            "</script>")
    List<AdminOrderItemDTO> selectAdminOrderItemList(Page<AdminOrderItemDTO> page, @Param("query") OrderItemPageQueryDTO query);

    @Update("<script>" +
            "INSERT INTO order_item (...) VALUES " +
            "<foreach collection='list' item='item' separator=','>(...)</foreach>" +
            "</script>")
    int batchInsert(@Param("list") List<OrderItem> orderItems);

    /**
     * 统计商品销量
     */
    @Select("SELECT product_id, SUM(quantity) as total_sales " +
            "FROM order_item " +
            "WHERE product_id = #{productId} " +
            "AND EXISTS (SELECT 1 FROM orders o WHERE o.order_no = order_item.order_no AND o.status = 'COMPLETED')" +
            "GROUP BY product_id")
    Integer selectProductSales(@Param("productId") Long productId);
}





