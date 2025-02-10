package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.model.dto.product.AdminProductDTO;
import com.example.model.dto.product.ProductDetailDTO;
import com.example.model.dto.product.ProductPageQueryDTO;
import com.example.model.entity.Products;
import com.example.model.enums.ProductStatusEnum;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;



import java.util.List;

/**
 * @author 31815
 * @description 针对表【products(商品表)】的数据库操作Mapper
 * @createDate 2025-02-10 02:08:50
 * @Entity gg.model.Products
 */
@Mapper
public interface ProductsMapper extends BaseMapper<Products> {

    /**
     * 分页查询商品（管理员用）
     */
    @Select("<script>" +
            "SELECT p.id, p.name, c.name AS category_name, p.price, p.stock, " +
            "p.status AS status_desc, p.create_time, p.update_time, " +
            "(SELECT SUM(quantity) FROM order_item WHERE product_id = p.id) AS sales_volume " +
            "FROM products p " +
            "LEFT JOIN category c ON p.category_id = c.id " +
            "<where>" +
            "   <if test='query.name != null'>AND p.name LIKE CONCAT('%',#{query.name},'%')</if>" +
            "   <if test='query.categoryId != null'>AND p.category_id = #{query.categoryId}</if>" +
            "   <if test='query.minPrice != null'>AND p.price &gt;= #{query.minPrice}</if>" +
            "   <if test='query.maxPrice != null'>AND p.price &lt;= #{query.maxPrice}</if>" +
            "   <if test='query.status != null'>AND p.status = #{query.status}</if>" +
            "   <if test='query.hasStock != null'>" +
            "       <if test='query.hasStock'>AND p.stock > 0</if>" +
            "       <if test='!query.hasStock'>AND p.stock = 0</if>" +
            "   </if>" +
            "</where>" +
            "ORDER BY p.create_time DESC" +
            "</script>")
    List<AdminProductDTO> selectAdminProductList(Page<AdminProductDTO> page, 
                                                @Param("query") ProductPageQueryDTO query);

    /**
     * 更新商品库存（带乐观锁）
     */
    @Update("UPDATE products SET stock = stock - #{quantity}, version = version + 1 " +
            "WHERE id = #{productId} AND stock >= #{quantity} AND version = #{version}")
    int deductStock(@Param("productId") Long productId,
                  @Param("quantity") Integer quantity,
                  @Param("version") Integer version);

    /**
     * 获取商品详情
     */
    @Select("SELECT p.id, p.name, c.name AS category_name, p.description, p.price, " +
            "p.stock, p.images, p.status AS status_desc, " +
            "(SELECT AVG(rating) FROM product_review WHERE product_id = p.id) AS average_rating, " +
            "(SELECT COUNT(*) FROM product_review WHERE product_id = p.id) AS review_count " +
            "FROM products p " +
            "LEFT JOIN category c ON p.category_id = c.id " +
            "WHERE p.id = #{productId}")
    ProductDetailDTO selectProductDetail(@Param("productId") Long productId);

    /**
     * 批量更新商品状态
     */
    @Update("<script>" +
            "UPDATE products SET status = #{status} " +
            "WHERE id IN " +
            "<foreach collection='productIds' item='id' open='(' separator=',' close=')'>" +
            "#{id}" +
            "</foreach>" +
            "</script>")
    int batchUpdateStatus(@Param("productIds") List<Long> productIds,
                        @Param("status") ProductStatusEnum status);

}





