package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mapper.UserPreferenceMapper;
import com.example.model.entity.Product;
import com.example.model.entity.UserBehavior;
import com.example.model.entity.UserPreference;
import com.example.service.ProductService;
import com.example.service.ProductSimilarityService;
import com.example.service.UserBehaviorService;
import com.example.service.UserPreferenceService;
import com.example.util.SimilarityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 28619
 * @description 针对表【user_preference(用户偏好表)】的数据库操作Service实现
 * @createDate 2025-04-09 17:42:48
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserPreferenceServiceImpl extends ServiceImpl<UserPreferenceMapper, UserPreference>
        implements UserPreferenceService {

    private final UserBehaviorService userBehaviorService;
    private final ProductService productService;
    private final ProductSimilarityService productSimilarityService;


    /**
     * 将double转换为BigDecimal，保留6位小数
     */
    private BigDecimal toBigDecimal(double value) {
        return BigDecimal.valueOf(value).setScale(6, RoundingMode.HALF_UP);
    }

    /**
     * 计算并更新所有用户对商品的偏好程度
     * 基于用户行为数据计算
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "userPreference", allEntries = true)
    public int calculateAndUpdateAllUserPreferences() {
        log.info("开始计算所有用户的商品偏好");

        // 获取所有用户行为数据
        List<UserBehavior> behaviors = userBehaviorService.list();
        if (behaviors.isEmpty()) {
            log.warn("没有用户行为数据，无法计算偏好");
            return 0;
        }

        // 按用户ID分组
        Map<Long, List<UserBehavior>> userBehaviors = behaviors.stream()
                .collect(Collectors.groupingBy(UserBehavior::getUserId));

        // 获取行为权重
        Map<Integer, Double> behaviorWeights = SimilarityUtil.getDefaultBehaviorWeights();

        // 存储所有用户偏好
        List<UserPreference> allPreferences = new ArrayList<>();

        // 遍历每个用户计算偏好
        for (Map.Entry<Long, List<UserBehavior>> entry : userBehaviors.entrySet()) {
            Long userId = entry.getKey();
            List<UserBehavior> userBehaviorList = entry.getValue();

            if (userBehaviorList.isEmpty()) {
                log.info("用户{}没有行为数据，跳过处理", userId);
                continue;
            }

            // 按商品ID分组
            Map<Long, List<UserBehavior>> productBehaviors = userBehaviorList.stream()
                    .collect(Collectors.groupingBy(UserBehavior::getProductId));

            // 计算每个商品的偏好分数
            for (Map.Entry<Long, List<UserBehavior>> productEntry : productBehaviors.entrySet()) {
                Long productId = productEntry.getKey();
                List<UserBehavior> productBehaviorList = productEntry.getValue();

                double score = 0.0;
                // 计算所有行为的加权得分
                for (UserBehavior behavior : productBehaviorList) {
                    Integer behaviorType = behavior.getBehaviorType();
                    double weight = behaviorWeights.getOrDefault(behaviorType, 1.0);
                    score += weight;
                }

                // 创建用户偏好对象
                UserPreference preference = createUserPreference(userId, productId, score);
                allPreferences.add(preference);
            }
        }

        // 规范化分数到[0,1]区间
        if (!allPreferences.isEmpty()) {
            // 按用户分组
            Map<Long, List<UserPreference>> userPreferences = allPreferences.stream()
                    .collect(Collectors.groupingBy(UserPreference::getUserId));

            // 对每个用户的偏好分数进行规范化
            for (List<UserPreference> userPrefs : userPreferences.values()) {
                double maxScore = userPrefs.stream()
                        .mapToDouble(p -> p.getPreferenceScore().doubleValue())
                        .max()
                        .orElse(1.0);

                if (maxScore > 0) {
                    for (UserPreference pref : userPrefs) {
                        double normalizedScore = pref.getPreferenceScore().doubleValue() / maxScore;
                        pref.setPreferenceScore(toBigDecimal(normalizedScore));
                    }
                }
            }
        }

        // 先删除所有现有偏好记录
        remove(null);

        // 批量保存所有偏好记录
        boolean success = saveBatch(allPreferences);

        log.info("用户偏好计算完成，共更新 {} 条记录", allPreferences.size());
        return success ? allPreferences.size() : 0;
    }

    /**
     * 计算并更新指定用户对商品的偏好程度
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "userPreference", key = "'user:' + #userId + ':preferences'")
    public int calculateAndUpdateUserPreferences(Long userId) {
        if (userId == null) {
            log.warn("用户ID为空，无法计算偏好");
            return 0;
        }

        log.info("开始计算用户{}的商品偏好", userId);

        // 获取该用户的所有行为数据
        LambdaQueryWrapper<UserBehavior> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserBehavior::getUserId, userId);
        List<UserBehavior> behaviors = userBehaviorService.list(queryWrapper);

        if (behaviors.isEmpty()) {
            log.warn("用户{}没有行为数据，无法计算偏好", userId);
            return 0;
        }

        // 获取行为权重
        Map<Integer, Double> behaviorWeights = SimilarityUtil.getDefaultBehaviorWeights();

        // 计算用户偏好
        Map<Long, Double> productScores = calculateUserPreferences(userId, behaviors, behaviorWeights);

        // 转换为实体对象
        List<UserPreference> userPreferences = productScores.entrySet().stream()
                .map(e -> createUserPreference(userId, e.getKey(), e.getValue()))
                .collect(Collectors.toList());

        if (userPreferences.isEmpty()) {
            log.info("用户{}没有计算出有效的偏好数据", userId);
            return 0;
        }

        // 删除该用户的所有现有偏好记录
        LambdaQueryWrapper<UserPreference> deleteWrapper = new LambdaQueryWrapper<>();
        deleteWrapper.eq(UserPreference::getUserId, userId);
        remove(deleteWrapper);

        // 批量保存新的偏好记录
        boolean success = saveBatch(userPreferences);

        log.info("用户{}的偏好计算完成，共更新 {} 条记录", userId, userPreferences.size());
        return success ? userPreferences.size() : 0;
    }

    /**
     * 获取用户对商品的偏好程度
     */
    @Override
    @Cacheable(value = "userPreference", key = "'user:' + #userId + ':product:' + #productId + ':score'")
    public double getUserPreferenceScore(Long userId, Long productId) {
        if (userId == null || productId == null) {
            return 0.0;
        }

        LambdaQueryWrapper<UserPreference> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserPreference::getUserId, userId)
                .eq(UserPreference::getProductId, productId);

        UserPreference preference = getOne(queryWrapper);

        if (preference == null || preference.getPreferenceScore() == null) {
            return 0.0;
        }

        return preference.getPreferenceScore().doubleValue();
    }

    /**
     * 获取用户对商品的偏好程度映射
     */
    @Override
    @Cacheable(value = "userPreference", key = "'user:' + #userId + ':scores'")
    public Map<Long, Double> getUserPreferenceScores(Long userId) {
        if (userId == null) {
            return new HashMap<>();
        }

        LambdaQueryWrapper<UserPreference> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserPreference::getUserId, userId);

        List<UserPreference> preferences = list(queryWrapper);

        return preferences.stream()
                .collect(Collectors.toMap(
                        UserPreference::getProductId,
                        p -> p.getPreferenceScore().doubleValue()
                ));
    }

    /**
     * 获取与用户偏好相似的用户列表
     */
    @Override
    @Cacheable(value = "userPreference", key = "'user:' + #userId + ':similar:limit:' + #limit")
    public Map<Long, Double> getSimilarUsers(Long userId, int limit) {
        if (userId == null || limit <= 0) {
            return new HashMap<>();
        }

        // 获取当前用户的偏好
        Map<Long, Double> userPreferences = getUserPreferenceScores(userId);

        if (userPreferences.isEmpty()) {
            log.info("用户{}没有偏好数据，无法计算相似用户", userId);
            return new HashMap<>();
        }

        // 获取所有用户的ID（除了当前用户）
        LambdaQueryWrapper<UserPreference> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(UserPreference::getUserId)
                .ne(UserPreference::getUserId, userId)
                .groupBy(UserPreference::getUserId);

        List<UserPreference> userPreferencesList = list(queryWrapper);
        List<Long> otherUserIds = userPreferencesList.stream()
                .map(UserPreference::getUserId)
                .distinct()
                .collect(Collectors.toList());

        if (otherUserIds.isEmpty()) {
            log.info("系统中没有其他用户，无法计算相似用户");
            return new HashMap<>();
        }

        // 计算用户间的相似度
        Map<Long, Double> similarityMap = new HashMap<>();

        for (Long otherId : otherUserIds) {
            Map<Long, Double> otherPreferences = getUserPreferenceScores(otherId);

            if (!otherPreferences.isEmpty()) {
                // 使用余弦相似度计算用户相似度
                double similarity = SimilarityUtil.cosineSimilarity(userPreferences, otherPreferences);
                similarityMap.put(otherId, similarity);
            }
        }

        // 按相似度降序排序并限制数量
        return similarityMap.entrySet().stream()
                .sorted(Map.Entry.<Long, Double>comparingByValue().reversed())
                .limit(limit)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

    /**
     * 基于用户的协同过滤推荐
     */
    @Override
    @Cacheable(value = "userPreference", key = "'user:' + #userId + ':cf:limit:' + #limit")
    public List<Product> recommendProductsByUserCF(Long userId, int limit) {
        if (userId == null || limit <= 0) {
            return new ArrayList<>();
        }

        // 获取与当前用户相似的用户
        Map<Long, Double> similarUsers = getSimilarUsers(userId, 10);

        if (similarUsers.isEmpty()) {
            log.info("用户{}没有相似用户，无法通过用户协同过滤推荐", userId);
            return new ArrayList<>();
        }

        // 获取当前用户已交互过的商品
        Set<Long> interactedProductIds = getUserInteractedProducts(userId);

        // 从相似用户中获取推荐商品并计算加权分数
        Map<Long, Double> candidateProducts = new HashMap<>();

        for (Map.Entry<Long, Double> entry : similarUsers.entrySet()) {
            Long similarUserId = entry.getKey();
            double userSimilarity = entry.getValue();

            // 获取相似用户的偏好
            Map<Long, Double> userPreferences = getUserPreferenceScores(similarUserId);

            for (Map.Entry<Long, Double> preference : userPreferences.entrySet()) {
                Long productId = preference.getKey();
                double preferenceScore = preference.getValue();

                // 过滤掉用户已交互过的商品
                if (!interactedProductIds.contains(productId)) {
                    // 计算加权分数：用户相似度 * 偏好分数
                    double weightedScore = userSimilarity * preferenceScore;

                    // 累加到候选商品分数中
                    candidateProducts.merge(productId, weightedScore, Double::sum);
                }
            }
        }

        // 按分数降序排序并限制数量
        List<Long> recommendedProductIds = candidateProducts.entrySet().stream()
                .sorted(Map.Entry.<Long, Double>comparingByValue().reversed())
                .limit(limit)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        // 如果推荐不足，补充热门商品
        if (recommendedProductIds.size() < limit) {
            List<Product> hotProducts = productService.getHotProducts(limit - recommendedProductIds.size());
            List<Long> hotProductIds = hotProducts.stream()
                    .map(Product::getId)
                    .filter(id -> !recommendedProductIds.contains(id) && !interactedProductIds.contains(id))
                    .collect(Collectors.toList());

            recommendedProductIds.addAll(hotProductIds);
        }

        // 查询商品详情
        if (recommendedProductIds.isEmpty()) {
            return new ArrayList<>();
        }

        return productService.listByIds(recommendedProductIds);
    }

    /**
     * 基于内容的推荐
     */
    @Override
    @Cacheable(value = "userPreference", key = "'user:' + #userId + ':content:limit:' + #limit")
    public List<Product> recommendProductsByContent(Long userId, int limit) {
        if (userId == null || limit <= 0) {
            return new ArrayList<>();
        }

        // 获取用户偏好
        Map<Long, Double> userPreferences = getUserPreferenceScores(userId);

        if (userPreferences.isEmpty()) {
            log.info("用户{}没有偏好数据，无法通过内容推荐", userId);
            return productService.getHotProducts(limit);
        }

        // 获取用户已交互过的商品
        Set<Long> interactedProductIds = getUserInteractedProducts(userId);

        // 找出用户最喜欢的几个商品
        List<Long> favoriteProductIds = userPreferences.entrySet().stream()
                .sorted(Map.Entry.<Long, Double>comparingByValue().reversed())
                .limit(5)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        // 基于这些商品寻找相似商品
        Map<Long, Double> candidateProducts = new HashMap<>();

        for (Long productId : favoriteProductIds) {
            List<Product> similarProducts = productSimilarityService.getMostSimilarProducts(productId, 10);

            for (Product product : similarProducts) {
                // 过滤掉用户已交互过的商品
                if (!interactedProductIds.contains(product.getId())) {
                    // 获取当前喜爱商品的偏好分数
                    double preferenceScore = userPreferences.getOrDefault(productId, 0.0);

                    // 累加到候选商品分数中
                    candidateProducts.merge(product.getId(), preferenceScore, Double::sum);
                }
            }
        }

        // 按分数降序排序并限制数量
        List<Long> recommendedProductIds = candidateProducts.entrySet().stream()
                .sorted(Map.Entry.<Long, Double>comparingByValue().reversed())
                .limit(limit)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        // 如果推荐不足，补充热门商品
        if (recommendedProductIds.size() < limit) {
            List<Product> hotProducts = productService.getHotProducts(limit - recommendedProductIds.size());
            List<Long> hotProductIds = hotProducts.stream()
                    .map(Product::getId)
                    .filter(id -> !recommendedProductIds.contains(id) && !interactedProductIds.contains(id))
                    .collect(Collectors.toList());

            recommendedProductIds.addAll(hotProductIds);
        }

        // 查询商品详情
        if (recommendedProductIds.isEmpty()) {
            return new ArrayList<>();
        }

        return productService.listByIds(recommendedProductIds);
    }

    /**
     * 混合推荐
     */
    @Override
    @Cacheable(value = "userPreference", key = "'user:' + #userId + ':hybrid:limit:' + #limit")
    public List<Product> getHybridRecommendations(Long userId, int limit) {
        if (userId == null || limit <= 0) {
            return new ArrayList<>();
        }

        // 分配不同推荐算法的比例
        int cfLimit = limit / 2;
        int contentLimit = limit - cfLimit;

        // 获取用户已交互过的商品
        Set<Long> interactedProductIds = getUserInteractedProducts(userId);

        // 获取不同推荐方式的结果
        List<Product> cfRecommendations = recommendProductsByUserCF(userId, cfLimit);
        List<Product> contentRecommendations = recommendProductsByContent(userId, contentLimit);

        // 合并推荐结果，去重
        Set<Long> recommendedIds = new HashSet<>();
        List<Product> hybridRecommendations = new ArrayList<>();

        // 先加入协同过滤推荐结果
        for (Product product : cfRecommendations) {
            if (!interactedProductIds.contains(product.getId()) && recommendedIds.add(product.getId())) {
                hybridRecommendations.add(product);
            }
        }

        // 再加入基于内容的推荐结果
        for (Product product : contentRecommendations) {
            if (!interactedProductIds.contains(product.getId()) && recommendedIds.add(product.getId())) {
                hybridRecommendations.add(product);
            }
        }

        // 如果推荐不足，补充热门商品
        if (hybridRecommendations.size() < limit) {
            List<Product> hotProducts = productService.getHotProducts(limit - hybridRecommendations.size());
            for (Product product : hotProducts) {
                if (!interactedProductIds.contains(product.getId()) && recommendedIds.add(product.getId())) {
                    hybridRecommendations.add(product);
                }
            }
        }

        // 限制总数量
        return hybridRecommendations.stream()
                .limit(limit)
                .collect(Collectors.toList());
    }

    /**
     * 创建用户偏好对象
     */
    private UserPreference createUserPreference(Long userId, Long productId, Double score) {
        UserPreference preference = new UserPreference();
        preference.setUserId(userId);
        preference.setProductId(productId);
        preference.setPreferenceScore(toBigDecimal(score));
        return preference;
    }

    /**
     * 计算用户对商品的偏好分数
     */
    private Map<Long, Double> calculateUserPreferences(Long userId, List<UserBehavior> behaviors, Map<Integer, Double> behaviorWeights) {
        // 按商品ID分组
        Map<Long, List<UserBehavior>> productBehaviors = behaviors.stream()
                .collect(Collectors.groupingBy(UserBehavior::getProductId));

        Map<Long, Double> productScores = new HashMap<>();

        // 计算每个商品的偏好分数
        for (Map.Entry<Long, List<UserBehavior>> entry : productBehaviors.entrySet()) {
            Long productId = entry.getKey();
            List<UserBehavior> productBehaviorList = entry.getValue();

            double score = 0.0;

            // 计算所有行为的加权得分
            for (UserBehavior behavior : productBehaviorList) {
                Integer behaviorType = behavior.getBehaviorType();
                double weight = behaviorWeights.getOrDefault(behaviorType, 1.0);
                score += weight;
            }

            // 将分数加入到结果集
            productScores.put(productId, score);
        }

        // 规范化分数到[0,1]区间
        if (!productScores.isEmpty()) {
            double maxScore = productScores.values().stream()
                    .mapToDouble(Double::doubleValue)
                    .max()
                    .orElse(1.0);

            for (Map.Entry<Long, Double> entry : productScores.entrySet()) {
                double normalizedScore = entry.getValue() / maxScore;
                productScores.put(entry.getKey(), normalizedScore);
            }
        }

        return productScores;
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




