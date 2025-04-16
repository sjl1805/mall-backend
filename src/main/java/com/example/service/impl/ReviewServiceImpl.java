package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.ResultCode;
import com.example.exception.BusinessException;
import com.example.mapper.ReviewMapper;
import com.example.model.dto.ReviewDTO;
import com.example.model.entity.Product;
import com.example.model.entity.Review;
import com.example.model.entity.User;
import com.example.model.vo.ReviewVO;
import com.example.service.ProductService;
import com.example.service.ReviewService;
import com.example.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 28619
 * @description 针对表【review(用户评价表)】的数据库操作Service实现
 * @createDate 2025-04-09 17:42:40
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewServiceImpl extends ServiceImpl<ReviewMapper, Review>
        implements ReviewService {

    private final ProductService productService;
    private final UserService userService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long addReview(Long userId, ReviewDTO reviewDTO) {
        if (userId == null || reviewDTO == null || reviewDTO.getProductId() == null) {
            throw new BusinessException("参数错误", ResultCode.PARAM_ERROR);
        }

        // 检查商品是否存在
        Product product = productService.getById(reviewDTO.getProductId());
        if (product == null) {
            throw new BusinessException("商品不存在", ResultCode.PARAM_ERROR);
        }

        // 检查用户是否已经评价过该商品
        if (hasReviewed(userId, reviewDTO.getProductId())) {
            throw new BusinessException("您已评价过该商品", ResultCode.PARAM_ERROR);
        }

        // 创建评价记录
        Review review = new Review();
        BeanUtils.copyProperties(reviewDTO, review);
        review.setUserId(userId);

        // 默认非匿名
        if (review.getAnonymous() == null) {
            review.setAnonymous(0);
        }

        // 保存评价
        save(review);

        return review.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteReview(Long userId, Long reviewId) {
        if (userId == null || reviewId == null) {
            throw new BusinessException("参数错误", ResultCode.PARAM_ERROR);
        }

        // 查询评价
        Review review = getById(reviewId);
        if (review == null) {
            throw new BusinessException("评价不存在", ResultCode.NOT_FOUND);
        }

        // 验证评价是否属于该用户
        if (!review.getUserId().equals(userId)) {
            throw new BusinessException("无权删除该评价", ResultCode.FORBIDDEN);
        }

        // 删除评价
        boolean result = removeById(reviewId);

        return result;
    }

    @Override
    public Page<ReviewVO> getProductReviews(Long productId, long page, long size) {
        if (productId == null) {
            throw new BusinessException("商品ID不能为空", ResultCode.PARAM_ERROR);
        }

        // 查询评价分页
        LambdaQueryWrapper<Review> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Review::getProductId, productId);
        queryWrapper.orderByDesc(Review::getCreateTime);

        Page<Review> reviewPage = page(new Page<>(page, size), queryWrapper);

        return convertToReviewVOPage(reviewPage);
    }

    @Override
    public Page<ReviewVO> getUserReviews(Long userId, long page, long size) {
        if (userId == null) {
            throw new BusinessException("用户ID不能为空", ResultCode.PARAM_ERROR);
        }

        // 查询评价分页
        LambdaQueryWrapper<Review> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Review::getUserId, userId);
        queryWrapper.orderByDesc(Review::getCreateTime);

        Page<Review> reviewPage = page(new Page<>(page, size), queryWrapper);

        return convertToReviewVOPage(reviewPage);
    }

    @Override
    public Map<String, Object> getProductReviewStats(Long productId) {
        if (productId == null) {
            throw new BusinessException("商品ID不能为空", ResultCode.PARAM_ERROR);
        }

        // 查询所有评价
        LambdaQueryWrapper<Review> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Review::getProductId, productId);
        List<Review> reviews = list(queryWrapper);

        // 统计总数
        int total = reviews.size();

        // 计算平均分
        double avgRating = 0;
        if (total > 0) {
            avgRating = reviews.stream()
                    .mapToInt(Review::getRating)
                    .average()
                    .orElse(0);
        }

        // 统计各评分数量
        Map<Integer, Long> ratingCounts = reviews.stream()
                .collect(Collectors.groupingBy(Review::getRating, Collectors.counting()));

        // 构造结果
        Map<String, Object> result = new HashMap<>();
        result.put("total", total);
        result.put("avgRating", String.format("%.1f", avgRating));
        result.put("ratingCounts", ratingCounts);

        // 计算好评率（4-5分为好评）
        long goodCount = reviews.stream()
                .filter(r -> r.getRating() >= 4)
                .count();
        double goodRate = total > 0 ? (double) goodCount / total * 100 : 0;
        result.put("goodRate", String.format("%.1f", goodRate) + "%");

        return result;
    }

    @Override
    public boolean hasReviewed(Long userId, Long productId) {
        if (userId == null || productId == null) {
            return false;
        }

        LambdaQueryWrapper<Review> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Review::getUserId, userId);
        queryWrapper.eq(Review::getProductId, productId);

        return count(queryWrapper) > 0;
    }

    @Override
    public List<ReviewVO> getLatestReviews(int limit) {
        if (limit <= 0) {
            limit = 10; // 默认获取10条
        }

        // 查询最新评价
        LambdaQueryWrapper<Review> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Review::getCreateTime);
        queryWrapper.last("LIMIT " + limit);

        List<Review> reviews = list(queryWrapper);

        return convertToReviewVOList(reviews);
    }

    /**
     * 将评价分页转换为评价VO分页
     */
    private Page<ReviewVO> convertToReviewVOPage(Page<Review> reviewPage) {
        List<ReviewVO> reviewVOs = convertToReviewVOList(reviewPage.getRecords());

        // 构造VO分页
        Page<ReviewVO> reviewVOPage = new Page<>(reviewPage.getCurrent(), reviewPage.getSize(), reviewPage.getTotal());
        reviewVOPage.setRecords(reviewVOs);

        return reviewVOPage;
    }

    /**
     * 将评价列表转换为评价VO列表
     */
    private List<ReviewVO> convertToReviewVOList(List<Review> reviews) {
        if (reviews.isEmpty()) {
            return new ArrayList<>();
        }

        // 获取关联的用户和商品信息
        Set<Long> userIds = reviews.stream()
                .map(Review::getUserId)
                .collect(Collectors.toSet());

        // 创建最终的映射变量
        final Map<Long, User> userMap;
        if (!userIds.isEmpty()) {
            List<User> users = userService.listByIds(userIds);
            userMap = users.stream().collect(Collectors.toMap(User::getId, user -> user));
        } else {
            userMap = new HashMap<>();
        }

        Set<Long> productIds = reviews.stream()
                .map(Review::getProductId)
                .collect(Collectors.toSet());

        // 创建最终的映射变量
        final Map<Long, Product> productMap;
        if (!productIds.isEmpty()) {
            List<Product> products = productService.listByIds(productIds);
            productMap = products.stream().collect(Collectors.toMap(Product::getId, product -> product));
        } else {
            productMap = new HashMap<>();
        }

        // 转换为VO
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return reviews.stream().map(review -> {
            ReviewVO reviewVO = new ReviewVO();
            BeanUtils.copyProperties(review, reviewVO);

            // 设置用户信息（如果是匿名评价，则隐藏用户信息）
            if (review.getAnonymous() == 0 && userMap.containsKey(review.getUserId())) {
                User user = userMap.get(review.getUserId());
                reviewVO.setNickname(user.getNickname());
                reviewVO.setAvatar(user.getAvatar());
            } else {
                reviewVO.setNickname("匿名用户");
                reviewVO.setAvatar(null);
            }

            // 设置商品信息
            if (productMap.containsKey(review.getProductId())) {
                Product product = productMap.get(review.getProductId());
                reviewVO.setProductName(product.getName());
                reviewVO.setProductImage(product.getImage());
            }

            // 格式化时间
            if (review.getCreateTime() != null) {
                reviewVO.setCreateTimeStr(sdf.format(review.getCreateTime()));
            }

            return reviewVO;
        }).collect(Collectors.toList());
    }
}




