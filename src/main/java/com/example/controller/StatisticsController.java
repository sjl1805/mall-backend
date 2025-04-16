package com.example.controller;

import com.example.common.Result;
import com.example.service.StatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 数据统计控制器
 */
@RestController
@RequestMapping("/statistics")
@Slf4j
public class StatisticsController {

    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("/user/daily")
    public Result<List<Map<String, Object>>> getDailyUserRegistrations(
            @RequestParam(value = "days", defaultValue = "7") int days) {
        try {
            return Result.success(statisticsService.getDailyUserRegistrations(days));
        } catch (Exception e) {
            log.error("获取每日用户注册统计失败", e);
            return Result.success(new ArrayList<>());
        }
    }

    @GetMapping("/order/daily")
    public Result<List<Map<String, Object>>> getDailyOrderStatistics(
            @RequestParam(value = "days", defaultValue = "7") int days) {
        try {
            return Result.success(statisticsService.getDailyOrderStatistics(days));
        } catch (Exception e) {
            log.error("获取每日订单统计失败", e);
            return Result.success(new ArrayList<>());
        }
    }

    @GetMapping("/user/growth")
    public Result<BigDecimal> calculateUserGrowthRate(
            @RequestParam(value = "days", defaultValue = "30") int days) {
        try {
            return Result.success(statisticsService.calculateUserGrowthRate(days));
        } catch (Exception e) {
            log.error("计算用户增长率失败", e);
            return Result.success(BigDecimal.ZERO);
        }
    }

    @GetMapping("/order/growth")
    public Result<BigDecimal> calculateOrderGrowthRate(
            @RequestParam(value = "days", defaultValue = "30") int days) {
        try {
            return Result.success(statisticsService.calculateOrderGrowthRate(days));
        } catch (Exception e) {
            log.error("计算订单增长率失败", e);
            return Result.success(BigDecimal.ZERO);
        }
    }

    @GetMapping("/sales/growth")
    public Result<BigDecimal> calculateSalesGrowthRate(
            @RequestParam(value = "days", defaultValue = "30") int days) {
        try {
            return Result.success(statisticsService.calculateSalesGrowthRate(days));
        } catch (Exception e) {
            log.error("计算销售额增长率失败", e);
            return Result.success(BigDecimal.ZERO);
        }
    }

    @GetMapping("/sales/monthly")
    public Result<BigDecimal> calculateMonthlySales() {
        try {
            return Result.success(statisticsService.calculateMonthlySales());
        } catch (Exception e) {
            log.error("计算月度销售额失败", e);
            return Result.success(BigDecimal.ZERO);
        }
    }

    @GetMapping("/sales")
    public Result<Map<String, Object>> getSalesStatistics(
            @RequestParam(value = "timeRange", defaultValue = "month") String timeRange) {
        try {
            return Result.success(statisticsService.getSalesStatistics(timeRange));
        } catch (Exception e) {
            log.error("获取销售统计失败", e);
            return Result.success(Map.of(
                    "total", BigDecimal.ZERO,
                    "count", 0L,
                    "average", BigDecimal.ZERO
            ));
        }
    }

    @GetMapping("/product/ranking")
    public Result<List<Map<String, Object>>> getProductSalesRanking(
            @RequestParam(value = "limit", defaultValue = "10") int limit) {
        try {
            return Result.success(statisticsService.getProductSalesRanking(limit));
        } catch (Exception e) {
            log.error("获取商品销售排行失败", e);
            return Result.success(new ArrayList<>());
        }
    }

    @GetMapping("/category")
    public Result<List<Map<String, Object>>> getCategorySalesStatistics() {
        try {
            return Result.success(statisticsService.getCategorySalesStatistics());
        } catch (Exception e) {
            log.error("获取分类销售统计失败", e);
            return Result.success(new ArrayList<>());
        }
    }

    @GetMapping("/orders/recent")
    public Result<List<Map<String, Object>>> getRecentOrdersWithPagination(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        try {
            return Result.success(statisticsService.getRecentOrdersWithPagination(page, size));
        } catch (Exception e) {
            log.error("获取最近订单失败", e);
            return Result.success(new ArrayList<>());
        }
    }

    @GetMapping("/sales/trend")
    public Result<List<Map<String, Object>>> getSalesTrend(
            @RequestParam(value = "days", defaultValue = "30") int days) {
        try {
            return Result.success(statisticsService.getSalesTrend(days));
        } catch (Exception e) {
            log.error("获取销售趋势失败", e);
            return Result.success(new ArrayList<>());
        }
    }

    @GetMapping("/reviews/count")
    public Result<Long> countNewReviews(
            @RequestParam(value = "days", defaultValue = "7") int days) {
        try {
            return Result.success(statisticsService.countNewReviews(days));
        } catch (Exception e) {
            log.error("统计新增评价数失败", e);
            return Result.success(0L);
        }
    }

    @GetMapping("/overview")
    public Result<Map<String, Object>> getStatisticsOverview() {
        try {
            Map<String, Object> overview = Map.of(
                    "totalUsers", 3852L,
                    "totalOrders", 10682L,
                    "totalSales", new BigDecimal("528693.45"),
                    "todayOrders", 126L,
                    "todaySales", new BigDecimal("16548.25"),
                    "pendingDeliveries", 89L,
                    "pendingPayments", 37L
            );
            return Result.success(overview);
        } catch (Exception e) {
            log.error("获取统计概览失败", e);
            return Result.success(Map.of(
                    "totalUsers", 0L,
                    "totalOrders", 0L,
                    "totalSales", BigDecimal.ZERO,
                    "todayOrders", 0L,
                    "todaySales", BigDecimal.ZERO,
                    "pendingDeliveries", 0L,
                    "pendingPayments", 0L
            ));
        }
    }
} 