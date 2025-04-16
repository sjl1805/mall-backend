package com.example.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.Result;
import com.example.model.entity.Product;
import com.example.model.vo.UserBehaviorVO;
import com.example.service.UserBehaviorService;
import com.example.util.UserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户行为控制器
 */
@Slf4j
@RestController
@RequestMapping("/user/behavior")
@RequiredArgsConstructor
public class UserBehaviorController {

    private final UserBehaviorService userBehaviorService;
    private final UserUtil userUtil;


    /**
     * 分页获取用户行为历史（视图对象）
     *
     * @param behaviorType 行为类型，可选
     * @param startTime    开始时间，可选
     * @param endTime      结束时间，可选
     * @param page         页码，默认1
     * @param size         每页数量，默认10
     * @return 用户行为分页
     */
    @GetMapping("/page")
    public Result<Page<UserBehaviorVO>> getUserBehaviorHistoryPage(
            @RequestParam(required = false) Integer behaviorType,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date startTime,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endTime,
            @RequestParam(defaultValue = "1") long page,
            @RequestParam(defaultValue = "10") long size) {
        Long userId = userUtil.getCurrentUserId();
        Page<UserBehaviorVO> behaviorPage = userBehaviorService.getUserBehaviorHistoryVO(
                userId, behaviorType, startTime, endTime, page, size);
        return Result.success(behaviorPage);
    }

    /*
     * 记录用户行为
     */
    @PostMapping("/record")
    public Result<Boolean> recordBehavior(
            @RequestParam(required = false) Long productId,
            @RequestParam(required = false) Integer behaviorType) {
        Long userId = userUtil.getCurrentUserId();
        boolean result = userBehaviorService.recordBehavior(userId, productId, behaviorType);
        return Result.success(result, "记录成功");
    }

    /**
     * 获取用户最近浏览的商品
     *
     * @param limit 数量限制，默认10
     * @return 商品列表
     */
    @GetMapping("/recent/view")
    public Result<List<Product>> getRecentViewedProducts(
            @RequestParam(defaultValue = "10") int limit) {
        Long userId = userUtil.getCurrentUserId();
        List<Product> products = userBehaviorService.getRecentViewedProducts(userId, limit);
        return Result.success(products);
    }

    /**
     * 获取用户行为统计数据
     *
     * @return 各类行为的统计数据
     */
    @GetMapping("/stats")
    public Result<Map<Integer, Long>> getUserBehaviorStats() {
        Long userId = userUtil.getCurrentUserId();
        Map<Integer, Long> stats = userBehaviorService.getUserBehaviorStats(userId);
        return Result.success(stats);
    }


    /**
     * 批量记录用户行为
     *
     * @param productIds   商品ID列表
     * @param behaviorType 行为类型
     * @return 记录结果
     */
    @PostMapping("/record/batch")
    public Result<Boolean> recordBehaviorBatch(
            @RequestParam List<Long> productIds,
            @RequestParam Integer behaviorType) {
        Long userId = userUtil.getCurrentUserId();
        boolean result = userBehaviorService.recordBehaviorBatch(userId, productIds, behaviorType);
        return Result.success(result, "批量记录成功");
    }

    /**
     * 清除用户行为记录
     *
     * @param behaviorType 行为类型，可选
     * @param beforeTime   清除该时间之前的记录，可选
     * @return 清除结果
     */
    @DeleteMapping("/clear")
    public Result<Boolean> clearBehaviorRecords(
            @RequestParam(required = false) Integer behaviorType,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date beforeTime) {
        Long userId = userUtil.getCurrentUserId();
        boolean result = userBehaviorService.clearBehaviorRecords(userId, behaviorType, beforeTime);
        return Result.success(result, "清除成功");
    }

    /**
     * 取消某个行为记录
     *
     * @param productId    商品ID
     * @param behaviorType 行为类型
     * @return 取消结果
     */
    @DeleteMapping("/cancel")
    public Result<Boolean> cancelBehavior(
            @RequestParam Long productId,
            @RequestParam Integer behaviorType) {
        Long userId = userUtil.getCurrentUserId();
        boolean result = userBehaviorService.cancelBehavior(userId, productId, behaviorType);
        return Result.success(result, "取消成功");
    }

    /**
     * 获取行为类型描述
     *
     * @param behaviorType 行为类型
     * @return 描述文本
     */
    @GetMapping("/type-desc")
    public Result<Map<String, String>> getBehaviorTypeDesc(@RequestParam Integer behaviorType) {
        String desc = userBehaviorService.getBehaviorTypeDesc(behaviorType);
        Map<String, String> result = new HashMap<>();
        result.put("desc", desc);
        return Result.success(result);
    }
} 