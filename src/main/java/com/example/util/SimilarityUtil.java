package com.example.util;

import cn.hutool.core.map.MapUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 相似度计算工具类
 * 用于商品推荐系统中的相似度计算
 */
@Slf4j
public class SimilarityUtil {

    /**
     * 计算余弦相似度
     * 余弦相似度 = A·B / (|A| * |B|)
     * 适用于用户-物品评分数据，用于计算物品之间的相似度
     *
     * @param vectorA 向量A (物品A的用户评分/行为数据)
     * @param vectorB 向量B (物品B的用户评分/行为数据)
     * @return 余弦相似度，范围[0,1]，越接近1表示越相似
     */
    public static double cosineSimilarity(Map<Long, Double> vectorA, Map<Long, Double> vectorB) {
        if (MapUtil.isEmpty(vectorA) || MapUtil.isEmpty(vectorB)) {
            return 0.0;
        }

        // 获取两个向量共同的键
        Set<Long> intersection = new HashSet<>(vectorA.keySet());
        intersection.retainAll(vectorB.keySet());

        if (intersection.isEmpty()) {
            return 0.0;
        }

        // 计算点积
        double dotProduct = 0.0;
        for (Long key : intersection) {
            dotProduct += vectorA.get(key) * vectorB.get(key);
        }

        // 计算向量A的模
        double normA = 0.0;
        for (Double value : vectorA.values()) {
            normA += Math.pow(value, 2);
        }
        normA = Math.sqrt(normA);

        // 计算向量B的模
        double normB = 0.0;
        for (Double value : vectorB.values()) {
            normB += Math.pow(value, 2);
        }
        normB = Math.sqrt(normB);

        // 计算余弦相似度
        if (normA * normB == 0) {
            return 0.0;
        }

        return dotProduct / (normA * normB);
    }

    /**
     * 基于用户行为的商品相似度计算
     * 针对不同行为类型赋予不同权重，计算综合相似度
     *
     * @param productAUserBehaviors 商品A的用户行为数据 (用户ID -> 行为类型 -> 计数)
     * @param productBUserBehaviors 商品B的用户行为数据 (用户ID -> 行为类型 -> 计数)
     * @param weightMap             不同行为类型的权重 (行为类型 -> 权重)
     * @return 综合相似度，范围[0,1]
     */
    public static double behaviorBasedSimilarity(
            Map<Long, Map<Integer, Integer>> productAUserBehaviors,
            Map<Long, Map<Integer, Integer>> productBUserBehaviors,
            Map<Integer, Double> weightMap) {

        if (MapUtil.isEmpty(productAUserBehaviors) || MapUtil.isEmpty(productBUserBehaviors)) {
            return 0.0;
        }

        // 将用户行为数据转换为向量表示
        Map<Long, Double> vectorA = convertBehaviorToVector(productAUserBehaviors, weightMap);
        Map<Long, Double> vectorB = convertBehaviorToVector(productBUserBehaviors, weightMap);

        // 计算余弦相似度
        return cosineSimilarity(vectorA, vectorB);
    }

    /**
     * 将用户行为数据转换为向量表示
     *
     * @param userBehaviors 用户行为数据 (用户ID -> 行为类型 -> 计数)
     * @param weightMap     不同行为类型的权重 (行为类型 -> 权重)
     * @return 向量表示 (用户ID -> 加权得分)
     */
    private static Map<Long, Double> convertBehaviorToVector(
            Map<Long, Map<Integer, Integer>> userBehaviors,
            Map<Integer, Double> weightMap) {

        return userBehaviors.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> {
                            double score = 0.0;
                            for (Map.Entry<Integer, Integer> behavior : entry.getValue().entrySet()) {
                                int behaviorType = behavior.getKey();
                                int count = behavior.getValue();
                                double weight = weightMap.getOrDefault(behaviorType, 1.0);
                                score += count * weight;
                            }
                            return score;
                        }
                ));
    }

    /**
     * 获取默认的行为权重映射
     * 不同的行为类型赋予不同的权重，表示其重要程度
     *
     * @return 行为类型到权重的映射
     */
    public static Map<Integer, Double> getDefaultBehaviorWeights() {
        Map<Integer, Double> weightMap = new HashMap<>();
        // 行为类型：1-浏览，2-收藏，3-加购，4-购买，5-评价
        weightMap.put(1, 1.0);    // 浏览
        weightMap.put(2, 2.0);    // 收藏
        weightMap.put(3, 3.0);    // 加购
        weightMap.put(4, 5.0);    // 购买
        weightMap.put(5, 4.0);    // 评价
        return weightMap;
    }
} 