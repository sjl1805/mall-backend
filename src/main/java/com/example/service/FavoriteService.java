package com.example.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.model.entity.Favorite;
import com.example.model.entity.Product;

import java.util.List;

/**
 * @author 28619
 * @description 针对表【favorite(用户收藏表)】的数据库操作Service
 * @createDate 2025-04-09 17:42:24
 */
public interface FavoriteService extends IService<Favorite> {
    /**
     * 添加收藏
     *
     * @param userId    用户ID
     * @param productId 商品ID
     * @return 是否成功
     */
    boolean addFavorite(Long userId, Long productId);

    /**
     * 取消收藏
     *
     * @param userId    用户ID
     * @param productId 商品ID
     * @return 是否成功
     */
    boolean removeFavorite(Long userId, Long productId);

    /**
     * 检查商品是否已收藏
     *
     * @param userId    用户ID
     * @param productId 商品ID
     * @return 是否已收藏
     */
    boolean isFavorite(Long userId, Long productId);

    /**
     * 获取用户收藏列表
     *
     * @param userId 用户ID
     * @param page   页码
     * @param size   每页数量
     * @return 收藏的商品分页
     */
    Page<Product> getUserFavorites(Long userId, long page, long size);

    /**
     * 获取用户收藏数量
     *
     * @param userId 用户ID
     * @return 收藏数量
     */
    int getUserFavoriteCount(Long userId);

    /**
     * 获取用户最近收藏的商品
     *
     * @param userId 用户ID
     * @param limit  数量限制
     * @return 收藏商品列表
     */
    List<Product> getRecentFavorites(Long userId, int limit);
}
