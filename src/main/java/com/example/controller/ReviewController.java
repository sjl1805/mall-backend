package com.example.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.Result;
import com.example.model.dto.ReviewDTO;
import com.example.model.vo.ReviewVO;
import com.example.service.ReviewService;
import com.example.util.UserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 评价控制器
 */
@Slf4j
@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final UserUtil userUtil;

    /**
     * 添加商品评价
     *
     * @param reviewDTO 评价信息
     * @return 评价ID
     */
    @PostMapping("/add")
    public Result<Long> addReview(@RequestBody @Valid ReviewDTO reviewDTO) {
        Long userId = userUtil.getCurrentUserId();
        Long reviewId = reviewService.addReview(userId, reviewDTO);
        return Result.success(reviewId, "评价成功");
    }

    /**
     * 删除评价
     *
     * @param reviewId 评价ID
     * @return 删除结果
     */
    @DeleteMapping("/{reviewId}")
    public Result<Boolean> deleteReview(@PathVariable Long reviewId) {
        Long userId = userUtil.getCurrentUserId();
        boolean result = reviewService.deleteReview(userId, reviewId);
        return Result.success(result, "删除成功");
    }

    /**
     * 获取商品评价列表
     *
     * @param productId 商品ID
     * @param page      页码
     * @param size      每页数量
     * @return 评价分页
     */
    @GetMapping("/product/{productId}")
    public Result<Page<ReviewVO>> getProductReviews(
            @PathVariable Long productId,
            @RequestParam(defaultValue = "1") long page,
            @RequestParam(defaultValue = "10") long size) {
        Page<ReviewVO> reviews = reviewService.getProductReviews(productId, page, size);
        return Result.success(reviews);
    }

    /**
     * 获取用户评价列表
     *
     * @param page 页码
     * @param size 每页数量
     * @return 评价分页
     */
    @GetMapping("/user")
    public Result<Page<ReviewVO>> getUserReviews(
            @RequestParam(defaultValue = "1") long page,
            @RequestParam(defaultValue = "10") long size) {
        Long userId = userUtil.getCurrentUserId();
        Page<ReviewVO> reviews = reviewService.getUserReviews(userId, page, size);
        return Result.success(reviews);
    }

    /**
     * 获取商品评价统计
     *
     * @param productId 商品ID
     * @return 评价统计信息
     */
    @GetMapping("/stats/{productId}")
    public Result<Map<String, Object>> getProductReviewStats(@PathVariable Long productId) {
        Map<String, Object> stats = reviewService.getProductReviewStats(productId);
        return Result.success(stats);
    }

    /**
     * 检查用户是否已评价商品
     *
     * @param productId 商品ID
     * @return 是否已评价
     */
    @GetMapping("/check")
    public Result<Map<String, Boolean>> checkReviewed(@RequestParam Long productId) {
        Long userId = userUtil.getCurrentUserId();
        boolean hasReviewed = reviewService.hasReviewed(userId, productId);

        Map<String, Boolean> result = new HashMap<>();
        result.put("reviewed", hasReviewed);

        return Result.success(result);
    }

    /**
     * 获取最新评价列表
     *
     * @param limit 数量限制
     * @return 评价列表
     */
    @GetMapping("/latest")
    public Result<List<ReviewVO>> getLatestReviews(
            @RequestParam(defaultValue = "5") int limit) {
        List<ReviewVO> reviews = reviewService.getLatestReviews(limit);
        return Result.success(reviews);
    }
} 