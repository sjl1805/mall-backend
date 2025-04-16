package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.model.entity.Product;
import com.example.model.entity.Recommendation;

import java.util.List;
import java.util.Map;

/**
 * @author 28619
 * @description 针对表【recommendation(推荐结果表)】的数据库操作Service
 * @createDate 2025-04-09 17:42:38
 */
public interface RecommendationService extends IService<Recommendation> {

    /**
     * 推荐类型：基于用户的协同过滤
     */
    int TYPE_USER_BASED = 1;

    /**
     * 推荐类型：基于物品的协同过滤
     */
    int TYPE_ITEM_BASED = 2;

    /**
     * 推荐类型：热门推荐
     */
    int TYPE_POPULAR = 3;

    /**
     * 推荐类型：综合推荐
     */
    int TYPE_HYBRID = 4;

    /**
     * 为指定用户生成推荐结果并保存
     * 计算基于用户的协同过滤推荐结果，并保存到推荐表中
     *
     * @param userId 用户ID
     * @param limit  推荐数量限制
     * @return 受影响的记录数
     */
    int generateUserBasedRecommendations(Long userId, int limit);

    /**
     * 为指定用户生成基于物品的推荐结果并保存
     * 根据用户的历史行为，找出与其交互过的物品相似的其他物品
     *
     * @param userId 用户ID
     * @param limit  推荐数量限制
     * @return 受影响的记录数
     */
    int generateItemBasedRecommendations(Long userId, int limit);

    /**
     * 为指定用户生成热门商品推荐并保存
     * 根据全站热门商品，过滤掉用户已交互过的商品
     *
     * @param userId 用户ID
     * @param limit  推荐数量限制
     * @return 受影响的记录数
     */
    int generatePopularRecommendations(Long userId, int limit);

    /**
     * 为指定用户生成混合推荐结果并保存
     * 融合多种推荐算法的结果
     *
     * @param userId 用户ID
     * @param limit  推荐数量限制
     * @return 受影响的记录数
     */
    int generateHybridRecommendations(Long userId, int limit);

    /**
     * 更新所有用户的所有类型推荐结果
     * 适合定时任务调用，一次性更新所有用户的推荐
     *
     * @param limit 每种推荐类型的数量限制
     * @return 受影响的记录数
     */
    int updateAllUsersRecommendations(int limit);

    /**
     * 更新指定用户的所有类型推荐结果
     *
     * @param userId 用户ID
     * @param limit  每种推荐类型的数量限制
     * @return 受影响的记录数
     */
    int updateUserRecommendations(Long userId, int limit);

    /**
     * 获取用户的推荐商品列表
     *
     * @param userId        用户ID
     * @param recommendType 推荐类型
     * @param limit         数量限制
     * @return 推荐商品列表
     */
    List<Product> getRecommendedProducts(Long userId, Integer recommendType, int limit);

    /**
     * 获取用户所有类型的推荐商品
     *
     * @param userId 用户ID
     * @param limit  每种类型的数量限制
     * @return 按推荐类型分组的商品列表
     */
    Map<Integer, List<Product>> getAllTypeRecommendations(Long userId, int limit);

    /**
     * 获取推荐类型描述
     *
     * @param recommendType 推荐类型
     * @return 描述文本
     */
    String getRecommendTypeDesc(Integer recommendType);

    /**
     * 清除用户的推荐记录
     *
     * @param userId        用户ID
     * @param recommendType 推荐类型，为null则清除所有类型
     * @return 是否成功
     */
    boolean clearUserRecommendations(Long userId, Integer recommendType);

    /**
     * 根据用户行为实时更新用户的推荐结果
     * 当用户产生新的行为时调用，增量更新推荐结果
     *
     * @param userId       用户ID
     * @param productId    商品ID
     * @param behaviorType 行为类型
     * @param limit        推荐数量限制
     * @return 更新的记录数
     */
    int updateRecommendationsAfterBehavior(Long userId, Long productId, Integer behaviorType, int limit);
}
