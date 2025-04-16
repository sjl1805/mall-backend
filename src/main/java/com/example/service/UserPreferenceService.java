package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.model.entity.Product;
import com.example.model.entity.UserPreference;

import java.util.List;
import java.util.Map;

/**
 * @author 28619
 * @description 针对表【user_preference(用户偏好表)】的数据库操作Service
 * @createDate 2025-04-09 17:42:49
 */
public interface UserPreferenceService extends IService<UserPreference> {

    /**
     * 计算并更新所有用户对商品的偏好程度
     * 基于用户行为数据计算
     *
     * @return 更新的记录数
     */
    int calculateAndUpdateAllUserPreferences();

    /**
     * 计算并更新指定用户对商品的偏好程度
     *
     * @param userId 用户ID
     * @return 更新的记录数
     */
    int calculateAndUpdateUserPreferences(Long userId);

    /**
     * 获取用户对商品的偏好程度
     *
     * @param userId    用户ID
     * @param productId 商品ID
     * @return 偏好程度，0.0-1.0，值越大表示偏好程度越高
     */
    double getUserPreferenceScore(Long userId, Long productId);

    /**
     * 获取用户对商品的偏好程度映射
     *
     * @param userId 用户ID
     * @return 商品ID到偏好程度的映射
     */
    Map<Long, Double> getUserPreferenceScores(Long userId);

    /**
     * 获取与用户偏好相似的用户列表
     *
     * @param userId 用户ID
     * @param limit  数量限制
     * @return 相似用户ID列表及相似度
     */
    Map<Long, Double> getSimilarUsers(Long userId, int limit);

    /**
     * 基于用户的协同过滤推荐
     * 通过寻找相似用户，推荐他们喜欢但当前用户未接触过的商品
     *
     * @param userId 用户ID
     * @param limit  推荐数量
     * @return 推荐商品列表
     */
    List<Product> recommendProductsByUserCF(Long userId, int limit);

    /**
     * 基于内容的推荐
     * 根据用户的历史偏好，推荐具有相似特征的商品
     *
     * @param userId 用户ID
     * @param limit  推荐数量
     * @return 推荐商品列表
     */
    List<Product> recommendProductsByContent(Long userId, int limit);

    /**
     * 混合推荐
     * 结合多种推荐算法的结果
     *
     * @param userId 用户ID
     * @param limit  推荐数量
     * @return 推荐商品列表
     */
    List<Product> getHybridRecommendations(Long userId, int limit);
}
