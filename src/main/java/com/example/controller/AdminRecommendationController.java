package com.example.controller;

import com.example.annotation.RequiresRole;
import com.example.common.Result;
import com.example.service.ProductSimilarityService;
import com.example.service.RecommendationService;
import com.example.service.UserPreferenceService;
import com.example.util.UserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 管理员推荐管理控制器
 * 需要管理员角色才能访问
 */
@Slf4j
@RestController
@RequestMapping("/admin/recommendation")
@RequiredArgsConstructor
@RequiresRole(1) // 管理员角色值为1
public class AdminRecommendationController {

    private final UserPreferenceService userPreferenceService;
    private final ProductSimilarityService productSimilarityService;
    private final RecommendationService recommendationService;
    private final UserUtil userUtil;

    /**
     * 更新所有用户偏好
     * 这是一个耗时操作，建议在系统负载较低时执行
     *
     * @return 更新结果
     */
    @PostMapping("/preferences/update-all")
    public Result<Map<String, Object>> updateAllUserPreferences() {
        log.info("开始更新所有用户偏好");
        long startTime = System.currentTimeMillis();

        // 计算并更新所有用户偏好
        int updatedCount = userPreferenceService.calculateAndUpdateAllUserPreferences();

        // 计算执行时间
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        // 构建返回结果
        Map<String, Object> result = new HashMap<>();
        result.put("updatedCount", updatedCount);
        result.put("executionTime", executionTime);
        result.put("message", String.format("成功更新了%d个用户的偏好记录，耗时%d毫秒", updatedCount, executionTime));

        log.info("更新所有用户偏好完成，共更新{}条记录，耗时{}毫秒", updatedCount, executionTime);
        return Result.success(result);
    }

    /**
     * 计算用户偏好
     *
     * @param userId 用户ID，为空则计算所有用户
     * @return 计算结果
     */
    @PostMapping("/calculate/preference")
    public Result<Integer> calculateUserPreferences(@RequestParam(required = false) Long userId) {
        int count;
        if (userId != null) {
            count = userPreferenceService.calculateAndUpdateUserPreferences(userId);
            return Result.success(count, "成功更新用户偏好记录" + count + "条");
        } else {
            count = userPreferenceService.calculateAndUpdateAllUserPreferences();
            return Result.success(count, "成功更新所有用户偏好记录" + count + "条");
        }
    }

    /**
     * 生成用户推荐结果
     *
     * @param userId 用户ID，为空则生成所有用户的推荐
     * @param limit  每种推荐类型的数量限制
     * @return 生成结果
     */
    @PostMapping("/calculate/recommendation")
    public Result<Integer> calculateRecommendations(
            @RequestParam(required = false) Long userId,
            @RequestParam(defaultValue = "10") int limit) {

        int count;
        if (userId != null) {
            count = recommendationService.updateUserRecommendations(userId, limit);
            return Result.success(count, "成功更新用户推荐记录" + count + "条");
        } else {
            count = recommendationService.updateAllUsersRecommendations(limit);
            return Result.success(count, "成功更新所有用户推荐记录" + count + "条");
        }
    }

    /**
     * 获取用户与其他用户的相似度
     *
     * @param userId 用户ID
     * @param limit  数量限制
     * @return 相似用户列表及相似度
     */
    @GetMapping("/similar-users")
    public Result<Map<String, Object>> getSimilarUsers(
            @RequestParam(required = false) Long userId,
            @RequestParam(defaultValue = "5") int limit) {

        // 如果未指定用户ID，使用当前登录用户
        if (userId == null) {
            userId = userUtil.getCurrentUserId();
        }

        Map<Long, Double> similarUsers = userPreferenceService.getSimilarUsers(userId, limit);

        Map<String, Object> result = new HashMap<>();
        result.put("userId", userId);
        result.put("similarUsers", similarUsers);

        return Result.success(result);
    }

    /**
     * 计算所有商品间的相似度
     *
     * @return 计算结果
     */
    @PostMapping("/products/similarity/calculate/all")
    public Result<Integer> calculateAllProductSimilarities() {
        log.info("开始计算所有商品间的相似度");
        int count = productSimilarityService.calculateAndUpdateAllProductSimilarities();
        return Result.success(count, "成功计算并更新了" + count + "条商品相似度数据");
    }

    /**
     * 计算指定商品与其他所有商品的相似度
     *
     * @param productId 商品ID
     * @return 计算结果
     */
    @PostMapping("/products/similarity/calculate/{productId}")
    public Result<Integer> calculateProductSimilarities(@PathVariable Long productId) {
        if (productId == null) {
            return Result.error("商品ID不能为空");
        }
        log.info("开始计算商品{}与其他商品的相似度", productId);
        int count = productSimilarityService.calculateAndUpdateSimilaritiesForProduct(productId);
        return Result.success(count, "成功计算并更新了" + count + "条商品相似度数据");
    }
} 