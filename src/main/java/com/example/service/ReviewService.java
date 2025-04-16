package com.example.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.model.dto.ReviewDTO;
import com.example.model.entity.Review;
import com.example.model.vo.ReviewVO;

import java.util.List;
import java.util.Map;

/**
 * @author 28619
 * @description 针对表【review(用户评价表)】的数据库操作Service
 * @createDate 2025-04-09 17:42:40
 */
public interface ReviewService extends IService<Review> {
    /**
     * 添加商品评价
     *
     * @param userId    用户ID
     * @param reviewDTO 评价信息
     * @return 评价ID
     */
    Long addReview(Long userId, ReviewDTO reviewDTO);

    /**
     * 删除评价
     *
     * @param userId   用户ID
     * @param reviewId 评价ID
     * @return 是否成功
     */
    boolean deleteReview(Long userId, Long reviewId);

    /**
     * 获取商品评价列表
     *
     * @param productId 商品ID
     * @param page      页码
     * @param size      每页数量
     * @return 评价分页
     */
    Page<ReviewVO> getProductReviews(Long productId, long page, long size);

    /**
     * 获取用户评价列表
     *
     * @param userId 用户ID
     * @param page   页码
     * @param size   每页数量
     * @return 评价分页
     */
    Page<ReviewVO> getUserReviews(Long userId, long page, long size);

    /**
     * 获取商品评价统计
     *
     * @param productId 商品ID
     * @return 评价统计信息，包括总数、平均分、各分数段数量等
     */
    Map<String, Object> getProductReviewStats(Long productId);

    /**
     * 检查用户是否已评价商品
     *
     * @param userId    用户ID
     * @param productId 商品ID
     * @return 是否已评价
     */
    boolean hasReviewed(Long userId, Long productId);

    /**
     * 获取最新评价列表
     *
     * @param limit 数量限制
     * @return 评价列表
     */
    List<ReviewVO> getLatestReviews(int limit);
}
