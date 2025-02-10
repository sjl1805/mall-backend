package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.model.dto.favorite.FavoriteItemDTO;
import com.example.model.dto.favorite.FavoritePageQueryDTO;
import com.example.model.entity.ProductFavorite;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author 31815
 * @description 针对表【product_favorite(商品收藏表)】的数据库操作Mapper
 * @createDate 2025-02-10 02:09:07
 * @Entity gg.model.ProductFavorite
 */
@Mapper
public interface ProductFavoriteMapper extends BaseMapper<ProductFavorite> {

    @Results(id = "favoriteItemMap", value = {
            @Result(property = "favoriteId", column = "favorite_id"),
            @Result(property = "productId", column = "product_id"),
            @Result(property = "productName", column = "product_name"),
            @Result(property = "mainImage", column = "main_image"),
            @Result(property = "collectTime", column = "collect_time"),
            @Result(property = "folderName", column = "folder_name")
    })
    @Select("<script>" +
            "SELECT pf.id AS favorite_id, p.id AS product_id, p.name AS product_name, " +
            "p.main_image, p.price, p.stock, pf.create_time AS collect_time, " +
            "ff.name AS folder_name " +
            "FROM product_favorite pf " +
            "JOIN products p ON pf.product_id = p.id " +
            "LEFT JOIN favorite_folder ff ON pf.folder_id = ff.id " +
            "WHERE pf.user_id = #{userId} " +
            "<if test='query.folderId != null'>AND pf.folder_id = #{query.folderId}</if>" +
            "<if test='query.productName != null'>AND p.name LIKE CONCAT('%',#{query.productName},'%')</if>" +
            "<if test='query.inStock != null'>" +
            "   <if test='query.inStock'>AND p.stock > 0</if>" +
            "   <if test='!query.inStock'>AND p.stock = 0</if>" +
            "</if>" +
            "ORDER BY pf.create_time DESC" +
            "</script>")
    IPage<FavoriteItemDTO> selectFavoriteItems(Page<FavoriteItemDTO> page,
                                              @Param("userId") Long userId,
                                              @Param("query") FavoritePageQueryDTO query);

    @Update("<script>" +
            "DELETE FROM product_favorite " +
            "WHERE id IN " +
            "<foreach collection='favoriteIds' item='id' open='(' separator=',' close=')'>" +
            "#{id}" +
            "</foreach>" +
            " AND user_id = #{userId} " +
            " AND product_id IN (SELECT id FROM products WHERE merchant_id = #{merchantId})"
            + "</script>")
    int batchDeleteFavorites(@Param("userId") Long userId,
                             @Param("favoriteIds") List<Long> favoriteIds,
                             @Param("merchantId") Long merchantId);

    @Options(useCache = true)
    @Select("SELECT COUNT(*) FROM product_favorite WHERE user_id = #{userId}")
    int countUserFavorites(@Param("userId") Long userId);

    /**
     * 检查是否已收藏
     */
    @Select("SELECT COUNT(*) FROM product_favorite " +
            "WHERE user_id = #{userId} AND product_id = #{productId}")
    int existsFavorite(@Param("userId") Long userId,
                       @Param("productId") Long productId);
}





