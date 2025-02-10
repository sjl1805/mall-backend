package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.model.dto.cart.CartItemDTO;
import com.example.model.dto.cart.CartPageQueryDTO;
import com.example.model.entity.Cart;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author 31815
 * @description 针对表【cart(购物车表)】的数据库操作Mapper
 * @createDate 2025-02-10 02:09:28
 * @Entity gg.model.Cart
 */
@Mapper
public interface CartMapper extends BaseMapper<Cart> {

    /**
     * 分页查询购物车项（带商品信息）
     */
    @Select("<script>" +
            "SELECT c.id as cart_id, c.product_id, c.quantity, c.checked, " +
            "p.name as product_name, JSON_UNQUOTE(JSON_EXTRACT(p.images, '$[0]')) as main_image, " +
            "p.price, p.stock " +
            "FROM cart c " +
            "LEFT JOIN products p ON c.product_id = p.id " +
            "WHERE c.user_id = #{userId} " +
            "<if test='query.productName != null'>AND p.name LIKE CONCAT('%',#{query.productName},'%')</if>" +
            "<if test='query.checkedStatus != null'>AND c.checked = #{query.checkedStatus}</if>" +
            "<if test='query.inStock != null and query.inStock'>AND p.stock > 0</if>" +
            "ORDER BY c.create_time DESC" +
            "</script>")
    List<CartItemDTO> selectCartItems(Page<CartItemDTO> page, 
                                    @Param("userId") Long userId,
                                    @Param("query") CartPageQueryDTO query);

    /**
     * 批量更新选中状态
     */
    @Update("<script>" +
            "UPDATE cart SET checked = #{checked} " +
            "WHERE id IN " +
            "<foreach collection='cartIds' item='id' open='(' separator=',' close=')'>" +
            "#{id}" +
            "</foreach>" +
            "</script>")
    int batchUpdateCheckedStatus(@Param("cartIds") List<Long> cartIds,
                                @Param("checked") Boolean checked);

    /**
     * 清空已选中的购物车项
     */
    @Update("DELETE FROM cart " +
            "WHERE user_id = #{userId} AND checked = true")
    int clearCheckedItems(@Param("userId") Long userId);

    /**
     * 统计用户购物车商品总数
     */
    @Select("SELECT IFNULL(SUM(quantity), 0) FROM cart " +
            "WHERE user_id = #{userId} AND checked = true")
    Integer countSelectedItems(@Param("userId") Long userId);

    /**
     * 更新商品数量（带乐观锁）
     */
    @Update("UPDATE cart SET quantity = #{quantity}, version = version + 1 " +
            "WHERE id = #{cartId} AND version = #{version}")
    int updateQuantityWithLock(@Param("cartId") Long cartId,
                             @Param("quantity") Integer quantity,
                             @Param("version") Integer version);
}




