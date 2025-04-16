package com.example.controller;

import com.example.common.Result;
import com.example.model.entity.Product;
import com.example.service.ProductService;
import com.example.service.ProductSimilarityService;
import com.example.service.RecommendationService;
import com.example.service.UserPreferenceService;
import com.example.util.UserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 推荐控制器
 * 处理与推荐系统相关的前台请求
 */
@RestController
@RequestMapping("/recommend")
@RequiredArgsConstructor
@Slf4j
public class RecommendationController {

    private final UserPreferenceService userPreferenceService;
    private final ProductSimilarityService productSimilarityService;
    private final RecommendationService recommendationService;
    private final ProductService productService;
    private final UserUtil userUtil;

    /**
     * 获取推荐商品列表（混合推荐）
     * 优先从推荐表中获取，如果没有记录，则实时计算
     *
     * @param limit 数量限制
     * @return 推荐商品列表
     */
    @GetMapping
    public Result<List<Product>> getRecommendations(@RequestParam(defaultValue = "10") int limit) {
        Long userId = userUtil.getCurrentUserId();

        // 先从推荐表中获取混合推荐结果
        List<Product> products = recommendationService.getRecommendedProducts(
                userId, RecommendationService.TYPE_HYBRID, limit);

        // 如果推荐表中没有记录，则实时计算
        if (products.isEmpty()) {
            log.info("用户{}没有预计算的混合推荐记录，进行实时计算", userId);
            products = userPreferenceService.getHybridRecommendations(userId, limit);

            // 异步更新推荐表
            // 注意：这里使用了简单的方式，实际应该使用异步任务
            new Thread(() -> {
                recommendationService.generateHybridRecommendations(userId, limit);
            }).start();
        }

        return Result.success(products);
    }

    /**
     * 获取所有推荐类型的商品
     * 包括：猜你喜欢、相似推荐、热门推荐、综合推荐
     *
     * @param limit 每种类型的数量限制
     * @return 所有类型的推荐商品
     */
    @GetMapping("/all-types")
    public Result<Map<String, Object>> getAllTypeRecommendations(@RequestParam(defaultValue = "5") int limit) {
        Long userId = userUtil.getCurrentUserId();

        // 获取所有类型的推荐
        Map<Integer, List<Product>> recommendations =
                recommendationService.getAllTypeRecommendations(userId, limit);

        // 转换为前端友好的格式
        Map<String, Object> result = new HashMap<>();
        for (Map.Entry<Integer, List<Product>> entry : recommendations.entrySet()) {
            String typeName = recommendationService.getRecommendTypeDesc(entry.getKey());
            result.put(typeName, entry.getValue());
        }

        return Result.success(result);
    }

    /**
     * 获取猜你喜欢推荐（基于用户的协同过滤）
     *
     * @param limit 数量限制
     * @return 推荐商品列表
     */
    @GetMapping("/user-cf")
    public Result<List<Product>> getUserCFRecommendations(@RequestParam(defaultValue = "10") int limit) {
        Long userId = userUtil.getCurrentUserId();

        // 先从推荐表中获取基于用户的推荐结果
        List<Product> products = recommendationService.getRecommendedProducts(
                userId, RecommendationService.TYPE_USER_BASED, limit);

        // 如果推荐表中没有记录，则实时计算
        if (products.isEmpty()) {
            log.info("用户{}没有预计算的基于用户的推荐记录，进行实时计算", userId);
            products = userPreferenceService.recommendProductsByUserCF(userId, limit);

            // 异步更新推荐表
            new Thread(() -> {
                recommendationService.generateUserBasedRecommendations(userId, limit);
            }).start();
        }

        return Result.success(products);
    }

    /**
     * 获取相似推荐（基于物品的协同过滤）
     *
     * @param limit 数量限制
     * @return 推荐商品列表
     */
    @GetMapping("/item-cf")
    public Result<List<Product>> getItemCFRecommendations(@RequestParam(defaultValue = "10") int limit) {
        Long userId = userUtil.getCurrentUserId();

        // 从推荐表中获取基于物品的推荐结果
        List<Product> products = recommendationService.getRecommendedProducts(
                userId, RecommendationService.TYPE_ITEM_BASED, limit);

        // 如果推荐表中没有记录，则实时生成
        if (products.isEmpty()) {
            log.info("用户{}没有预计算的基于物品的推荐记录，进行实时生成", userId);
            // 先生成推荐记录
            recommendationService.generateItemBasedRecommendations(userId, limit * 2);
            // 再次查询
            products = recommendationService.getRecommendedProducts(
                    userId, RecommendationService.TYPE_ITEM_BASED, limit);
        }

        return Result.success(products);
    }

    /**
     * 获取热门推荐
     *
     * @param limit 数量限制
     * @return 推荐商品列表
     */
    @GetMapping("/popular")
    public Result<List<Product>> getPopularRecommendations(@RequestParam(defaultValue = "10") int limit) {
        Long userId = userUtil.getCurrentUserId();

        // 从推荐表中获取热门推荐结果
        List<Product> products = recommendationService.getRecommendedProducts(
                userId, RecommendationService.TYPE_POPULAR, limit);

        // 如果推荐表中没有记录，则实时生成
        if (products.isEmpty()) {
            log.info("用户{}没有预计算的热门推荐记录，进行实时生成", userId);
            // 先生成推荐记录
            recommendationService.generatePopularRecommendations(userId, limit);
            // 再次查询
            products = recommendationService.getRecommendedProducts(
                    userId, RecommendationService.TYPE_POPULAR, limit);

            // 如果仍然为空，则直接返回热门商品
            if (products.isEmpty()) {
                products = productService.getHotProducts(limit);
            }
        }

        return Result.success(products);
    }

    /**
     * 基于内容的推荐
     *
     * @param limit 数量限制
     * @return 推荐商品列表
     */
    @GetMapping("/content-based")
    public Result<List<Product>> getContentBasedRecommendations(@RequestParam(defaultValue = "10") int limit) {
        Long userId = userUtil.getCurrentUserId();
        List<Product> products = userPreferenceService.recommendProductsByContent(userId, limit);
        return Result.success(products);
    }

    /**
     * 获取与商品相似的商品
     *
     * @param productId 商品ID
     * @param limit     数量限制
     * @return 相似商品列表
     */
    @GetMapping("/similar/{productId}")
    public Result<List<Product>> getSimilarProducts(
            @PathVariable Long productId,
            @RequestParam(defaultValue = "5") int limit) {
        if (productId == null) {
            return Result.error("商品ID不能为空");
        }
        List<Product> products = productSimilarityService.getMostSimilarProducts(productId, limit);
        return Result.success(products);
    }
} 