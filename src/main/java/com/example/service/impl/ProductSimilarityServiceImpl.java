package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mapper.ProductSimilarityMapper;
import com.example.model.entity.Product;
import com.example.model.entity.ProductSimilarity;
import com.example.model.entity.UserBehavior;
import com.example.service.ProductService;
import com.example.service.ProductSimilarityService;
import com.example.service.UserBehaviorService;
import com.example.util.SimilarityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 28619
 * @description 针对表【product_similarity(商品相似度表)】的数据库操作Service实现
 * @createDate 2025-04-09 17:42:38
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ProductSimilarityServiceImpl extends ServiceImpl<ProductSimilarityMapper, ProductSimilarity>
        implements ProductSimilarityService {

    private final UserBehaviorService userBehaviorService;
    private final ProductService productService;

    /**
     * 将double转换为BigDecimal，保留6位小数
     */
    private BigDecimal toBigDecimal(double value) {
        return BigDecimal.valueOf(value).setScale(6, RoundingMode.HALF_UP);
    }

    /**
     * 计算并更新所有商品间的相似度
     * 这是一个批量操作，会计算所有商品之间的相似度并更新到数据库
     * 适合定时任务执行，如每天凌晨执行一次
     *
     * @return 计算更新的商品对数量
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "productSimilarity", allEntries = true)
    public int calculateAndUpdateAllProductSimilarities() {
        // 获取所有商品ID
        List<Product> products = productService.list();
        if (products.isEmpty()) {
            log.warn("没有商品数据，无法计算相似度");
            return 0;
        }

        List<Long> productIds = products.stream()
                .map(Product::getId)
                .collect(Collectors.toList());

        return calculateAndUpdateProductSimilarities(productIds);
    }

    /**
     * 计算并更新指定商品集合间的相似度
     *
     * @param productIds 需要计算相似度的商品ID集合
     * @return 计算更新的商品对数量
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "productSimilarity", allEntries = true)
    public int calculateAndUpdateProductSimilarities(List<Long> productIds) {
        if (productIds == null || productIds.isEmpty()) {
            log.warn("商品ID列表为空，无法计算相似度");
            return 0;
        }

        log.info("开始计算{}个商品间的相似度", productIds.size());

        // 获取所有相关的用户行为数据
        Map<Long, Map<Long, Map<Integer, Integer>>> productUserBehaviors =
                getUserBehaviorsForProducts(productIds);

        // 获取行为权重配置
        Map<Integer, Double> behaviorWeights = SimilarityUtil.getDefaultBehaviorWeights();

        // 计算并批量更新相似度
        List<ProductSimilarity> similarities = calculateSimilaritiesBatch(
                productIds, productUserBehaviors, behaviorWeights);

        if (similarities.isEmpty()) {
            log.info("没有计算出有效的相似度数据");
            return 0;
        }

        try {
            // 收集需要处理的商品ID对
            // 物理删除这些商品对应的相似度记录
            if (!productIds.isEmpty()) {
                // 使用QueryWrapper构建条件，避免直接拼接SQL
                QueryWrapper<ProductSimilarity> deleteWrapper = new QueryWrapper<>();
                deleteWrapper.in("product_id", productIds)
                        .or()
                        .in("similar_product_id", productIds);

                // 执行删除操作
                getBaseMapper().delete(deleteWrapper);
            }

            // 批量保存新的相似度数据
            saveBatch(similarities);

            log.info("成功计算并更新了{}条商品相似度数据", similarities.size());
            return similarities.size();
        } catch (Exception e) {
            log.error("计算并更新商品相似度时发生错误", e);
            throw e; // 抛出异常以触发事务回滚
        }
    }

    /**
     * 计算指定商品与其他所有商品的相似度
     * 适用于新增商品或商品信息有较大变更时调用
     *
     * @param productId 商品ID
     * @return 计算更新的相似度对数量
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "productSimilarity", key = "'similar:' + #productId")
    public int calculateAndUpdateSimilaritiesForProduct(Long productId) {
        if (productId == null) {
            log.warn("商品ID为空，无法计算相似度");
            return 0;
        }

        // 获取所有其他商品ID
        List<Long> otherProductIds = productService.list().stream()
                .map(Product::getId)
                .filter(id -> !id.equals(productId))
                .collect(Collectors.toList());

        if (otherProductIds.isEmpty()) {
            log.warn("没有其他商品数据，无法计算相似度");
            return 0;
        }

        log.info("开始计算商品{}与其他{}个商品的相似度", productId, otherProductIds.size());

        // 构建包含目标商品的列表
        List<Long> allProductIds = new ArrayList<>(otherProductIds);
        allProductIds.add(productId);

        // 获取所有相关的用户行为数据
        Map<Long, Map<Long, Map<Integer, Integer>>> productUserBehaviors =
                getUserBehaviorsForProducts(allProductIds);

        // 获取行为权重配置
        Map<Integer, Double> behaviorWeights = SimilarityUtil.getDefaultBehaviorWeights();

        // 计算商品与其他每个商品的相似度
        List<ProductSimilarity> similarities = new ArrayList<>();
        Map<Long, Map<Integer, Integer>> targetProductBehaviors =
                productUserBehaviors.getOrDefault(productId, new HashMap<>());

        for (Long otherId : otherProductIds) {
            Map<Long, Map<Integer, Integer>> otherProductBehaviors =
                    productUserBehaviors.getOrDefault(otherId, new HashMap<>());

            // 计算相似度
            double similarity = SimilarityUtil.behaviorBasedSimilarity(
                    targetProductBehaviors, otherProductBehaviors, behaviorWeights);

            // 如果相似度过低，可以不记录
            if (similarity > 0.01) {
                // 创建或更新相似度记录，注意需要双向都记录
                // A相似于B
                ProductSimilarity sim1 = new ProductSimilarity();
                sim1.setProductId(productId);
                sim1.setSimilarProductId(otherId);
                sim1.setSimilarity(toBigDecimal(similarity));
                similarities.add(sim1);

                // B相似于A
                ProductSimilarity sim2 = new ProductSimilarity();
                sim2.setProductId(otherId);
                sim2.setSimilarProductId(productId);
                sim2.setSimilarity(toBigDecimal(similarity));
                similarities.add(sim2);
            }
        }

        if (similarities.isEmpty()) {
            log.info("没有计算出有效的相似度数据");
            return 0;
        }

        try {
            // 物理删除该商品与其他商品的相似度记录，而不是使用逻辑删除
            getBaseMapper().delete(
                    new QueryWrapper<ProductSimilarity>()
                            .eq("product_id", productId)
                            .or()
                            .eq("similar_product_id", productId)
            );

            // 批量保存新的相似度数据
            saveBatch(similarities);

            log.info("成功计算并更新了{}条商品相似度数据", similarities.size());
            return similarities.size();
        } catch (Exception e) {
            log.error("计算并更新商品相似度时发生错误", e);
            throw e; // 抛出异常以触发事务回滚
        }
    }

    /**
     * 获取与商品最相似的N个商品
     *
     * @param productId 商品ID
     * @param limit     数量限制
     * @return 相似商品列表
     */
    @Override
    @Cacheable(value = "productSimilarity", key = "'similar:' + #productId + ':limit:' + #limit")
    public List<Product> getMostSimilarProducts(Long productId, int limit) {
        if (productId == null || limit <= 0) {
            return new ArrayList<>();
        }

        // 查询相似度记录
        LambdaQueryWrapper<ProductSimilarity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ProductSimilarity::getProductId, productId)
                .orderByDesc(ProductSimilarity::getSimilarity)
                .last("LIMIT " + limit);

        List<ProductSimilarity> similarities = list(queryWrapper);
        if (similarities.isEmpty()) {
            return new ArrayList<>();
        }

        // 获取相似商品ID列表
        List<Long> similarProductIds = similarities.stream()
                .map(ProductSimilarity::getSimilarProductId)
                .collect(Collectors.toList());

        // 查询商品详细信息
        return productService.listByIds(similarProductIds);
    }

    /**
     * 批量计算商品间的相似度
     */
    private List<ProductSimilarity> calculateSimilaritiesBatch(
            List<Long> productIds,
            Map<Long, Map<Long, Map<Integer, Integer>>> productUserBehaviors,
            Map<Integer, Double> behaviorWeights) {

        List<ProductSimilarity> similarities = new ArrayList<>();

        // 计算所有商品对之间的相似度
        for (int i = 0; i < productIds.size(); i++) {
            Long productIdA = productIds.get(i);
            Map<Long, Map<Integer, Integer>> productABehaviors =
                    productUserBehaviors.getOrDefault(productIdA, new HashMap<>());

            for (int j = i + 1; j < productIds.size(); j++) {
                Long productIdB = productIds.get(j);
                Map<Long, Map<Integer, Integer>> productBBehaviors =
                        productUserBehaviors.getOrDefault(productIdB, new HashMap<>());

                // 计算相似度
                double similarity = SimilarityUtil.behaviorBasedSimilarity(
                        productABehaviors, productBBehaviors, behaviorWeights);

                // 如果相似度过低，可以不记录
                if (similarity > 0.01) {
                    // 创建相似度记录(A->B)
                    ProductSimilarity sim1 = new ProductSimilarity();
                    sim1.setProductId(productIdA);
                    sim1.setSimilarProductId(productIdB);
                    sim1.setSimilarity(toBigDecimal(similarity));
                    similarities.add(sim1);

                    // 创建相似度记录(B->A)
                    ProductSimilarity sim2 = new ProductSimilarity();
                    sim2.setProductId(productIdB);
                    sim2.setSimilarProductId(productIdA);
                    sim2.setSimilarity(toBigDecimal(similarity));
                    similarities.add(sim2);
                }
            }
        }

        return similarities;
    }

    /**
     * 获取多个商品的用户行为数据
     * 返回格式：商品ID -> 用户ID -> 行为类型 -> 计数
     */
    private Map<Long, Map<Long, Map<Integer, Integer>>> getUserBehaviorsForProducts(List<Long> productIds) {
        // 查询这些商品的所有用户行为数据
        LambdaQueryWrapper<UserBehavior> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(UserBehavior::getProductId, productIds);
        List<UserBehavior> behaviors = userBehaviorService.list(queryWrapper);

        // 按商品ID分组整理数据
        Map<Long, Map<Long, Map<Integer, Integer>>> result = new HashMap<>();

        for (UserBehavior behavior : behaviors) {
            Long productId = behavior.getProductId();
            Long userId = behavior.getUserId();
            Integer behaviorType = behavior.getBehaviorType();

            // 获取或创建商品的用户行为映射
            Map<Long, Map<Integer, Integer>> productBehaviors =
                    result.computeIfAbsent(productId, k -> new HashMap<>());

            // 获取或创建用户的行为类型映射
            Map<Integer, Integer> userBehaviors =
                    productBehaviors.computeIfAbsent(userId, k -> new HashMap<>());

            // 累加行为计数
            userBehaviors.put(behaviorType, userBehaviors.getOrDefault(behaviorType, 0) + 1);
        }

        return result;
    }
}




