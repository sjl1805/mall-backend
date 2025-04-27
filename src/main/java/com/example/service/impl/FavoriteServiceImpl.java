package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.ResultCode;
import com.example.exception.BusinessException;
import com.example.mapper.FavoriteMapper;
import com.example.model.entity.Favorite;
import com.example.model.entity.Product;
import com.example.service.FavoriteService;
import com.example.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 28619
 * @description 针对表【favorite(用户收藏表)】的数据库操作Service实现
 * @createDate 2025-04-09 17:42:24
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class FavoriteServiceImpl extends ServiceImpl<FavoriteMapper, Favorite>
        implements FavoriteService {

    private final ProductService productService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "favorite", key = "'user:' + #userId + ':favorites'")
    public boolean addFavorite(Long userId, Long productId) {
        if (userId == null || productId == null) {
            throw new BusinessException("参数错误", ResultCode.PARAM_ERROR);
        }

        // 检查商品是否存在
        Product product = productService.getById(productId);
        if (product == null) {
            throw new BusinessException("商品不存在", ResultCode.PARAM_ERROR);
        }

        // 检查是否已经收藏
        if (isFavorite(userId, productId)) {
            return true; // 已收藏，直接返回成功
        }

        // 创建收藏记录
        Favorite favorite = new Favorite();
        favorite.setUserId(userId);
        favorite.setProductId(productId);

        boolean result = save(favorite);

        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "favorite", key = "'user:' + #userId + ':favorites'")
    public boolean removeFavorite(Long userId, Long productId) {
        if (userId == null || productId == null) {
            throw new BusinessException("参数错误", ResultCode.PARAM_ERROR);
        }

        LambdaQueryWrapper<Favorite> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Favorite::getUserId, userId);
        queryWrapper.eq(Favorite::getProductId, productId);

        boolean result = remove(queryWrapper);

        return result;
    }

    @Override
    @Cacheable(value = "favorite", key = "'user:' + #userId + ':product:' + #productId + ':isFavorite'")
    public boolean isFavorite(Long userId, Long productId) {
        if (userId == null || productId == null) {
            return false;
        }

        LambdaQueryWrapper<Favorite> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Favorite::getUserId, userId);
        queryWrapper.eq(Favorite::getProductId, productId);

        return count(queryWrapper) > 0;
    }

    @Override
    @Cacheable(value = "favorite", key = "'user:' + #userId + ':favorites:page:' + #page + ':size:' + #size")
    public Page<Product> getUserFavorites(Long userId, long page, long size) {
        if (userId == null) {
            throw new BusinessException("用户ID不能为空", ResultCode.PARAM_ERROR);
        }

        // 获取收藏记录分页
        LambdaQueryWrapper<Favorite> favoriteQueryWrapper = new LambdaQueryWrapper<>();
        favoriteQueryWrapper.eq(Favorite::getUserId, userId);
        favoriteQueryWrapper.orderByDesc(Favorite::getCreateTime);

        Page<Favorite> favoritePage = page(new Page<>(page, size), favoriteQueryWrapper);

        // 转换为商品分页
        List<Long> productIds = favoritePage.getRecords().stream()
                .map(Favorite::getProductId)
                .collect(Collectors.toList());

        List<Product> products = new ArrayList<>();
        if (!productIds.isEmpty()) {
            // 批量获取商品信息
            products = productService.listByIds(productIds);

            // 保持原收藏顺序
            products.sort((p1, p2) -> {
                int index1 = productIds.indexOf(p1.getId());
                int index2 = productIds.indexOf(p2.getId());
                return Integer.compare(index1, index2);
            });
        }

        // 构造商品分页结果
        Page<Product> productPage = new Page<>(favoritePage.getCurrent(), favoritePage.getSize(), favoritePage.getTotal());
        productPage.setRecords(products);

        return productPage;
    }

    @Override
    @Cacheable(value = "favorite", key = "'user:' + #userId + ':count'")
    public int getUserFavoriteCount(Long userId) {
        if (userId == null) {
            return 0;
        }

        LambdaQueryWrapper<Favorite> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Favorite::getUserId, userId);

        return Math.toIntExact(count(queryWrapper));
    }

    @Override
    @Cacheable(value = "favorite", key = "'user:' + #userId + ':recent:' + #limit")
    public List<Product> getRecentFavorites(Long userId, int limit) {
        if (userId == null) {
            throw new BusinessException("用户ID不能为空", ResultCode.PARAM_ERROR);
        }

        if (limit <= 0) {
            limit = 10; // 默认获取10条
        }

        // 获取最近收藏记录
        LambdaQueryWrapper<Favorite> favoriteQueryWrapper = new LambdaQueryWrapper<>();
        favoriteQueryWrapper.eq(Favorite::getUserId, userId);
        favoriteQueryWrapper.orderByDesc(Favorite::getCreateTime);
        favoriteQueryWrapper.last("LIMIT " + limit);

        List<Favorite> favorites = list(favoriteQueryWrapper);

        // 获取收藏的商品信息
        List<Long> productIds = favorites.stream()
                .map(Favorite::getProductId)
                .collect(Collectors.toList());

        if (productIds.isEmpty()) {
            return new ArrayList<>();
        }

        List<Product> products = productService.listByIds(productIds);

        // 保持原收藏顺序
        products.sort((p1, p2) -> {
            int index1 = productIds.indexOf(p1.getId());
            int index2 = productIds.indexOf(p2.getId());
            return Integer.compare(index1, index2);
        });

        return products;
    }
}




