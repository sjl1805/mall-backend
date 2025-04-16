package com.example.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 统计服务接口
 * 提供各种数据统计和分析功能
 */
public interface StatisticsService {

    /**
     * 获取每日用户注册统计（最近n天）
     *
     * @param days 天数
     * @return 每日注册统计列表
     */
    List<Map<String, Object>> getDailyUserRegistrations(int days);

    /**
     * 获取每日订单统计（最近n天）
     *
     * @param days 天数
     * @return 每日订单统计列表
     */
    List<Map<String, Object>> getDailyOrderStatistics(int days);

    /**
     * 计算用户增长率（最近n天）
     *
     * @param days 天数
     * @return 增长率（百分比）
     */
    BigDecimal calculateUserGrowthRate(int days);

    /**
     * 计算订单增长率（最近n天）
     *
     * @param days 天数
     * @return 增长率（百分比）
     */
    BigDecimal calculateOrderGrowthRate(int days);

    /**
     * 计算销售额增长率（最近n天）
     *
     * @param days 天数
     * @return 增长率（百分比）
     */
    BigDecimal calculateSalesGrowthRate(int days);

    /**
     * 计算月度销售额
     *
     * @return 月销售额
     */
    BigDecimal calculateMonthlySales();

    /**
     * 获取销售额统计（按时间段）
     *
     * @param timeRange 时间范围：week-本周，month-本月，year-本年
     * @return 销售额统计
     */
    Map<String, Object> getSalesStatistics(String timeRange);

    /**
     * 获取商品销售排行榜
     *
     * @param limit 限制数量
     * @return 商品销售排行榜
     */
    List<Map<String, Object>> getProductSalesRanking(int limit);

    /**
     * 获取分类销售统计
     *
     * @return 分类销售统计
     */
    List<Map<String, Object>> getCategorySalesStatistics();

    /**
     * 获取最近订单列表（带分页）
     *
     * @param page 页码
     * @param size 每页数量
     * @return 订单列表
     */
    List<Map<String, Object>> getRecentOrdersWithPagination(int page, int size);

    /**
     * 获取销售趋势（最近n天）
     *
     * @param days 天数
     * @return 销售趋势
     */
    List<Map<String, Object>> getSalesTrend(int days);

    /**
     * 统计新增评价数（最近n天）
     *
     * @param days 天数
     * @return 评价数量
     */
    long countNewReviews(int days);
} 