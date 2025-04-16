package com.example.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.model.entity.Product;
import com.example.model.entity.UserBehavior;
import com.example.model.vo.UserBehaviorVO;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 28619
 * @description 针对表【user_behavior(用户行为记录表)】的数据库操作Service
 * @createDate 2025-04-09 17:42:46
 */
public interface UserBehaviorService extends IService<UserBehavior> {
    /**
     * 记录用户行为
     *
     * @param userId       用户ID
     * @param productId    商品ID
     * @param behaviorType 行为类型：1-浏览，2-收藏，3-加购，4-购买，5-评价
     * @return 是否成功
     */
    boolean recordBehavior(Long userId, Long productId, Integer behaviorType);

    /**
     * 批量记录用户行为
     *
     * @param userId       用户ID
     * @param productIds   商品ID列表
     * @param behaviorType 行为类型
     * @return 是否成功
     */
    boolean recordBehaviorBatch(Long userId, List<Long> productIds, Integer behaviorType);

    /**
     * 获取用户行为历史
     *
     * @param userId       用户ID
     * @param behaviorType 行为类型，为null则获取所有类型
     * @param startTime    开始时间，为null则不限制
     * @param endTime      结束时间，为null则不限制
     * @param limit        数量限制，默认100
     * @return 用户行为记录列表
     */
    List<UserBehavior> getUserBehaviorHistory(Long userId, Integer behaviorType,
                                              Date startTime, Date endTime, Integer limit);

    /**
     * 获取用户行为历史（视图对象）
     *
     * @param userId       用户ID
     * @param behaviorType 行为类型，为null则获取所有类型
     * @param startTime    开始时间，为null则不限制
     * @param endTime      结束时间，为null则不限制
     * @param page         页码
     * @param size         每页数量
     * @return 用户行为分页
     */
    Page<UserBehaviorVO> getUserBehaviorHistoryVO(Long userId, Integer behaviorType,
                                                  Date startTime, Date endTime, long page, long size);

    /**
     * 获取用户最近浏览的商品
     *
     * @param userId 用户ID
     * @param limit  数量限制
     * @return 商品列表
     */
    List<Product> getRecentViewedProducts(Long userId, int limit);

    /**
     * 获取用户行为统计
     *
     * @param userId 用户ID
     * @return 各类行为的统计数据
     */
    Map<Integer, Long> getUserBehaviorStats(Long userId);

    /**
     * 清除用户行为记录
     *
     * @param userId       用户ID
     * @param behaviorType 行为类型，为null则清除所有类型
     * @param beforeTime   清除该时间之前的记录，为null则清除所有
     * @return 是否成功
     */
    boolean clearBehaviorRecords(Long userId, Integer behaviorType, Date beforeTime);

    /**
     * 取消单个用户行为
     *
     * @param userId       用户ID
     * @param productId    商品ID
     * @param behaviorType 行为类型：1-浏览，2-收藏，3-加购，4-购买，5-评价
     * @return 是否成功
     */
    boolean cancelBehavior(Long userId, Long productId, Integer behaviorType);

    /**
     * 获取行为类型描述
     *
     * @param behaviorType 行为类型
     * @return 描述文本
     */
    String getBehaviorTypeDesc(Integer behaviorType);
}
