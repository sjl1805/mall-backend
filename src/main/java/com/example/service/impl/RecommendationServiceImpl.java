package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mapper.RecommendationMapper;
import com.example.model.entity.Product;
import com.example.model.entity.Recommendation;
import com.example.model.entity.UserBehavior;
import com.example.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 28619
 * @description 针对表【recommendation(推荐结果表)】的数据库操作Service实现
 * @createDate 2025-04-09 17:42:38
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class RecommendationServiceImpl extends ServiceImpl<RecommendationMapper, Recommendation>
        implements RecommendationService {

    private final UserPreferenceService userPreferenceService;
    private final ProductSimilarityService productSimilarityService;
    private final UserBehaviorService userBehaviorService;
    private final ProductService productService;
    private final UserService userService;

    /**
     * 将double转换为BigDecimal，保留6位小数
     */
    private BigDecimal toBigDecimal(double value) {
        return BigDecimal.valueOf(value).setScale(6, RoundingMode.HALF_UP);
    }

    /**
     * 为指定用户生成基于用户的协同过滤推荐结果并保存
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int generateUserBasedRecommendations(Long userId, int limit) {
        if (userId == null || limit <= 0) {
            log.warn("用户ID为空或limit参数无效，无法生成推荐");
            return 0;
        }

        log.info("开始为用户{}生成基于用户的协同过滤推荐", userId);

        // 获取推荐商品列表
        List<Product> recommendedProducts = userPreferenceService.recommendProductsByUserCF(userId, limit);

        if (recommendedProducts.isEmpty()) {
            log.info("没有为用户{}找到基于用户的推荐商品", userId);
            return 0;
        }

        // 清除该用户此类型的已有推荐
        clearUserRecommendations(userId, TYPE_USER_BASED);

        // 创建新的推荐记录
        List<Recommendation> recommendations = new ArrayList<>();

        // 从UserPreferenceService获取的推荐结果没有分数，这里按照位置依次递减分配权重
        double baseScore = 1.0;
        double decrement = 1.0 / (recommendedProducts.size() + 1);

        for (int i = 0; i < recommendedProducts.size(); i++) {
            Product product = recommendedProducts.get(i);
            double score = baseScore - i * decrement;

            Recommendation recommendation = new Recommendation();
            recommendation.setUserId(userId);
            recommendation.setProductId(product.getId());
            recommendation.setRecommendType(TYPE_USER_BASED);
            recommendation.setScore(toBigDecimal(score));
            recommendations.add(recommendation);
        }

        // 批量保存推荐记录
        boolean success = saveBatch(recommendations);

        int count = success ? recommendations.size() : 0;
        log.info("用户{}基于用户的协同过滤推荐生成完成，共{}条记录", userId, count);
        return count;
    }

    /**
     * 为指定用户生成基于物品的推荐结果并保存
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int generateItemBasedRecommendations(Long userId, int limit) {
        if (userId == null || limit <= 0) {
            log.warn("用户ID为空或limit参数无效，无法生成推荐");
            return 0;
        }

        log.info("开始为用户{}生成基于物品的协同过滤推荐", userId);

        // 获取用户已交互过的商品
        Set<Long> interactedProductIds = getUserInteractedProducts(userId);

        if (interactedProductIds.isEmpty()) {
            log.info("用户{}没有交互过的商品，无法生成基于物品的推荐", userId);
            return 0;
        }

        // 从交互过的商品中找出最近交互的几个商品
        List<UserBehavior> recentBehaviors = userBehaviorService.getUserBehaviorHistory(
                userId, null, null, null, 10);

        List<Long> recentProductIds = recentBehaviors.stream()
                .map(UserBehavior::getProductId)
                .distinct()
                .limit(5)  // 取最近交互的5个商品
                .collect(Collectors.toList());

        // 为每个商品找出相似商品
        Map<Long, Double> candidateProducts = new HashMap<>();

        for (Long productId : recentProductIds) {
            List<Product> similarProducts = productSimilarityService.getMostSimilarProducts(productId, 10);

            // 如果没有相似商品，跳过当前循环
            if (similarProducts.isEmpty()) {
                continue;
            }

            // 获取这些商品的相似度
            LambdaQueryWrapper<com.example.model.entity.ProductSimilarity> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(com.example.model.entity.ProductSimilarity::getProductId, productId)
                    .in(com.example.model.entity.ProductSimilarity::getSimilarProductId,
                            similarProducts.stream().map(Product::getId).collect(Collectors.toList()));

            List<com.example.model.entity.ProductSimilarity> similarities =
                    productSimilarityService.list(queryWrapper);

            Map<Long, Double> similarityMap = similarities.stream()
                    .collect(Collectors.toMap(
                            com.example.model.entity.ProductSimilarity::getSimilarProductId,
                            s -> s.getSimilarity().doubleValue()
                    ));

            // 添加候选商品，过滤已交互过的
            for (Product product : similarProducts) {
                Long similarProductId = product.getId();
                if (!interactedProductIds.contains(similarProductId)) {
                    double similarity = similarityMap.getOrDefault(similarProductId, 0.0);
                    // 如果商品已经在候选集中，取较大的相似度
                    candidateProducts.merge(similarProductId, similarity, Math::max);
                }
            }
        }

        // 如果候选商品不足，补充热门商品
        if (candidateProducts.size() < limit) {
            List<Product> hotProducts = productService.getHotProducts(limit - candidateProducts.size());
            for (Product product : hotProducts) {
                if (!interactedProductIds.contains(product.getId()) &&
                        !candidateProducts.containsKey(product.getId())) {
                    // 热门商品设置一个较低的基础分
                    candidateProducts.put(product.getId(), 0.3);
                }
            }
        }

        // 按相似度排序，取前limit个
        List<Map.Entry<Long, Double>> sortedCandidates = candidateProducts.entrySet().stream()
                .sorted(Map.Entry.<Long, Double>comparingByValue().reversed())
                .limit(limit)
                .collect(Collectors.toList());

        // 清除该用户此类型的已有推荐
        clearUserRecommendations(userId, TYPE_ITEM_BASED);

        // 创建新的推荐记录
        List<Recommendation> recommendations = new ArrayList<>();

        for (Map.Entry<Long, Double> entry : sortedCandidates) {
            Recommendation recommendation = new Recommendation();
            recommendation.setUserId(userId);
            recommendation.setProductId(entry.getKey());
            recommendation.setRecommendType(TYPE_ITEM_BASED);
            recommendation.setScore(toBigDecimal(entry.getValue()));
            recommendations.add(recommendation);
        }

        // 批量保存推荐记录
        boolean success = saveBatch(recommendations);

        int count = success ? recommendations.size() : 0;
        log.info("用户{}基于物品的协同过滤推荐生成完成，共{}条记录", userId, count);
        return count;
    }

    /**
     * 为指定用户生成热门商品推荐并保存
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int generatePopularRecommendations(Long userId, int limit) {
        if (userId == null || limit <= 0) {
            log.warn("用户ID为空或limit参数无效，无法生成推荐");
            return 0;
        }

        log.info("开始为用户{}生成热门商品推荐", userId);

        // 获取用户已交互过的商品
        Set<Long> interactedProductIds = getUserInteractedProducts(userId);

        // 获取热门商品
        List<Product> hotProducts = productService.getHotProducts(limit * 2);

        // 过滤掉用户已交互过的商品
        List<Product> filteredProducts = hotProducts.stream()
                .filter(p -> !interactedProductIds.contains(p.getId()))
                .limit(limit)
                .collect(Collectors.toList());

        if (filteredProducts.isEmpty()) {
            log.info("没有为用户{}找到合适的热门推荐商品", userId);
            return 0;
        }

        // 清除该用户此类型的已有推荐
        clearUserRecommendations(userId, TYPE_POPULAR);

        // 创建新的推荐记录
        List<Recommendation> recommendations = new ArrayList<>();

        // 根据热度排名分配分数
        double baseScore = 1.0;
        double decrement = 1.0 / (filteredProducts.size() + 1);

        for (int i = 0; i < filteredProducts.size(); i++) {
            Product product = filteredProducts.get(i);
            double score = baseScore - i * decrement;

            Recommendation recommendation = new Recommendation();
            recommendation.setUserId(userId);
            recommendation.setProductId(product.getId());
            recommendation.setRecommendType(TYPE_POPULAR);
            recommendation.setScore(toBigDecimal(score));
            recommendations.add(recommendation);
        }

        // 批量保存推荐记录
        boolean success = saveBatch(recommendations);

        int count = success ? recommendations.size() : 0;
        log.info("用户{}热门商品推荐生成完成，共{}条记录", userId, count);
        return count;
    }

    /**
     * 为指定用户生成混合推荐结果并保存
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int generateHybridRecommendations(Long userId, int limit) {
        if (userId == null || limit <= 0) {
            log.warn("用户ID为空或limit参数无效，无法生成推荐");
            return 0;
        }

        log.info("开始为用户{}生成混合推荐", userId);

        // 获取用户已交互过的商品
        Set<Long> interactedProductIds = getUserInteractedProducts(userId);

        // 获取混合推荐商品
        List<Product> hybridProducts = userPreferenceService.getHybridRecommendations(userId, limit);

        if (hybridProducts.isEmpty()) {
            log.info("没有为用户{}找到混合推荐商品", userId);
            return 0;
        }

        // 清除该用户此类型的已有推荐
        clearUserRecommendations(userId, TYPE_HYBRID);

        // 创建新的推荐记录
        List<Recommendation> recommendations = new ArrayList<>();

        double baseScore = 1.0;
        double decrement = 1.0 / (hybridProducts.size() + 1);

        for (int i = 0; i < hybridProducts.size(); i++) {
            Product product = hybridProducts.get(i);

            // 跳过用户已交互过的商品
            if (interactedProductIds.contains(product.getId())) {
                continue;
            }

            double score = baseScore - i * decrement;

            Recommendation recommendation = new Recommendation();
            recommendation.setUserId(userId);
            recommendation.setProductId(product.getId());
            recommendation.setRecommendType(TYPE_HYBRID);
            recommendation.setScore(toBigDecimal(score));
            recommendations.add(recommendation);
        }

        // 批量保存推荐记录
        boolean success = saveBatch(recommendations);

        int count = success ? recommendations.size() : 0;
        log.info("用户{}混合推荐生成完成，共{}条记录", userId, count);
        return count;
    }

    /**
     * 更新所有用户的所有类型推荐结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateAllUsersRecommendations(int limit) {
        log.info("开始更新所有用户的推荐结果");

        // 获取所有活跃用户
        List<Long> userIds = userService.list().stream()
                .map(user -> user.getId())
                .collect(Collectors.toList());

        if (userIds.isEmpty()) {
            log.info("没有用户，无需更新推荐");
            return 0;
        }

        int totalCount = 0;

        // 为每个用户更新推荐
        for (Long userId : userIds) {
            try {
                int userCount = updateUserRecommendations(userId, limit);
                totalCount += userCount;
            } catch (Exception e) {
                log.error("更新用户{}的推荐结果时发生错误", userId, e);
            }
        }

        log.info("所有用户的推荐结果更新完成，共更新{}条记录", totalCount);
        return totalCount;
    }

    /**
     * 更新指定用户的所有类型推荐结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateUserRecommendations(Long userId, int limit) {
        if (userId == null || limit <= 0) {
            log.warn("用户ID为空或limit参数无效，无法更新推荐");
            return 0;
        }

        log.info("开始更新用户{}的所有类型推荐结果", userId);

        int count = 0;

        try {
            // 生成各类型推荐
            count += generateUserBasedRecommendations(userId, limit);
            count += generateItemBasedRecommendations(userId, limit);
            count += generatePopularRecommendations(userId, limit);
            count += generateHybridRecommendations(userId, limit);

            log.info("用户{}的所有推荐类型更新完成，共{}条记录", userId, count);
        } catch (Exception e) {
            log.error("更新用户{}的推荐结果时发生错误", userId, e);
            throw e;
        }

        return count;
    }

    /**
     * 获取用户的推荐商品列表
     */
    @Override
    public List<Product> getRecommendedProducts(Long userId, Integer recommendType, int limit) {
        if (userId == null || limit <= 0) {
            return new ArrayList<>();
        }

        // 构建查询条件
        LambdaQueryWrapper<Recommendation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Recommendation::getUserId, userId);

        if (recommendType != null) {
            queryWrapper.eq(Recommendation::getRecommendType, recommendType);
        }

        queryWrapper.orderByDesc(Recommendation::getScore)
                .last("LIMIT " + limit);

        // 查询推荐记录
        List<Recommendation> recommendations = list(queryWrapper);

        if (recommendations.isEmpty()) {
            return new ArrayList<>();
        }

        // 提取商品ID
        List<Long> productIds = recommendations.stream()
                .map(Recommendation::getProductId)
                .collect(Collectors.toList());

        // 查询商品详情
        return productService.listByIds(productIds);
    }

    /**
     * 获取用户所有类型的推荐商品
     */
    @Override
    public Map<Integer, List<Product>> getAllTypeRecommendations(Long userId, int limit) {
        if (userId == null || limit <= 0) {
            return new HashMap<>();
        }

        Map<Integer, List<Product>> result = new HashMap<>();

        // 获取所有推荐类型
        int[] types = {TYPE_USER_BASED, TYPE_ITEM_BASED, TYPE_POPULAR, TYPE_HYBRID};

        for (int type : types) {
            List<Product> products = getRecommendedProducts(userId, type, limit);
            result.put(type, products);
        }

        return result;
    }

    /**
     * 获取推荐类型描述
     */
    @Override
    public String getRecommendTypeDesc(Integer recommendType) {
        if (recommendType == null) {
            return "未知推荐";
        }

        switch (recommendType) {
            case TYPE_USER_BASED:
                return "猜你喜欢";
            case TYPE_ITEM_BASED:
                return "相似推荐";
            case TYPE_POPULAR:
                return "热门推荐";
            case TYPE_HYBRID:
                return "综合推荐";
            default:
                return "未知推荐";
        }
    }

    /**
     * 清除用户的推荐记录
     */
    @Override
    public boolean clearUserRecommendations(Long userId, Integer recommendType) {
        if (userId == null) {
            return false;
        }

        LambdaQueryWrapper<Recommendation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Recommendation::getUserId, userId);

        if (recommendType != null) {
            queryWrapper.eq(Recommendation::getRecommendType, recommendType);
        }

        return remove(queryWrapper);
    }

    /**
     * 根据用户行为实时更新用户的推荐结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateRecommendationsAfterBehavior(Long userId, Long productId, Integer behaviorType, int limit) {
        if (userId == null || productId == null || behaviorType == null || limit <= 0) {
            log.warn("参数无效，无法更新推荐");
            return 0;
        }

        log.info("用户{}对商品{}产生了类型为{}的行为，开始更新推荐", userId, productId, behaviorType);

        int count = 0;

        try {
            // 购买和评价行为对推荐影响较大，更新所有类型的推荐
            if (behaviorType == 4 || behaviorType == 5) {
                count = updateUserRecommendations(userId, limit);
            } else {
                // 对于浏览、收藏、加购行为，主要更新基于物品和混合推荐
                count += generateItemBasedRecommendations(userId, limit);
                count += generateHybridRecommendations(userId, limit);
            }

            log.info("用户{}行为后的推荐更新完成，共{}条记录", userId, count);
        } catch (Exception e) {
            log.error("更新用户{}行为后的推荐结果时发生错误", userId, e);
            throw e;
        }

        return count;
    }

    /**
     * 获取用户已交互过的商品ID集合
     */
    private Set<Long> getUserInteractedProducts(Long userId) {
        LambdaQueryWrapper<UserBehavior> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserBehavior::getUserId, userId)
                .select(UserBehavior::getProductId);

        List<UserBehavior> behaviors = userBehaviorService.list(queryWrapper);

        return behaviors.stream()
                .map(UserBehavior::getProductId)
                .collect(Collectors.toSet());
    }
}




