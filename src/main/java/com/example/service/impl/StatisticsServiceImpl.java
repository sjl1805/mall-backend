package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.mapper.*;
import com.example.model.entity.*;
import com.example.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 统计服务实现类
 */
@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {


    // 添加必要的Mapper依赖
    private final UserMapper userMapper;
    private final OrderMapper orderMapper;
    private final ProductMapper productMapper;
    private final CategoryMapper categoryMapper;
    private final OrderItemMapper orderItemMapper;
    private final ReviewMapper reviewMapper;

    @Override
    public List<Map<String, Object>> getDailyUserRegistrations(int days) {
        List<Map<String, Object>> result = new ArrayList<>();
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (int i = 0; i < days; i++) {
            LocalDate date = today.minusDays(i);
            LocalDateTime startTime = date.atStartOfDay();
            LocalDateTime endTime = date.plusDays(1).atStartOfDay();

            // 查询当天注册的用户数量
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.ge(User::getCreateTime, startTime)
                    .lt(User::getCreateTime, endTime);
            long count = userMapper.selectCount(queryWrapper);

            Map<String, Object> dayData = new HashMap<>();
            dayData.put("date", date.format(formatter));
            dayData.put("count", count);
            result.add(dayData);
        }

        // 按日期排序
        result.sort(Comparator.comparing(m -> (String) m.get("date")));
        return result;
    }

    @Override
    public List<Map<String, Object>> getDailyOrderStatistics(int days) {
        List<Map<String, Object>> result = new ArrayList<>();
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (int i = 0; i < days; i++) {
            LocalDate date = today.minusDays(i);
            LocalDateTime startTime = date.atStartOfDay();
            LocalDateTime endTime = date.plusDays(1).atStartOfDay();

            // 查询当天订单数量
            LambdaQueryWrapper<Order> countWrapper = new LambdaQueryWrapper<>();
            countWrapper.ge(Order::getCreateTime, startTime)
                    .lt(Order::getCreateTime, endTime);
            long count = orderMapper.selectCount(countWrapper);

            // 查询当天订单总金额
            LambdaQueryWrapper<Order> amountWrapper = new LambdaQueryWrapper<>();
            amountWrapper.ge(Order::getCreateTime, startTime)
                    .lt(Order::getCreateTime, endTime);
            // 使用sum查询总销售额
            QueryWrapper<Order> sumWrapper = new QueryWrapper<>();
            sumWrapper.select("IFNULL(SUM(pay_amount), 0) as total_amount")
                    .ge("create_time", startTime)
                    .lt("create_time", endTime)
                    .eq("deleted", 0)
                    .gt("status", 0); // 只统计已支付的订单

            Map<String, Object> resultMap = orderMapper.selectMaps(sumWrapper).get(0);
            BigDecimal amount = BigDecimal.valueOf(
                    resultMap.get("total_amount") instanceof BigDecimal ?
                            ((BigDecimal) resultMap.get("total_amount")).doubleValue() :
                            Double.parseDouble(resultMap.get("total_amount").toString())
            );

            Map<String, Object> dayData = new HashMap<>();
            dayData.put("date", date.format(formatter));
            dayData.put("count", count);
            dayData.put("amount", amount);
            result.add(dayData);
        }

        // 按日期排序
        result.sort(Comparator.comparing(m -> (String) m.get("date")));
        return result;
    }

    @Override
    public BigDecimal calculateUserGrowthRate(int days) {
        LocalDateTime today = LocalDate.now().atStartOfDay();
        LocalDateTime previousPeriodStart = today.minusDays(days * 2L);
        LocalDateTime previousPeriodEnd = today.minusDays(days);

        // 当前时间段新增用户
        LambdaQueryWrapper<User> currentWrapper = new LambdaQueryWrapper<>();
        currentWrapper.ge(User::getCreateTime, previousPeriodEnd)
                .lt(User::getCreateTime, today);
        long currentPeriodUsers = userMapper.selectCount(currentWrapper);

        // 上一时间段新增用户
        LambdaQueryWrapper<User> previousWrapper = new LambdaQueryWrapper<>();
        previousWrapper.ge(User::getCreateTime, previousPeriodStart)
                .lt(User::getCreateTime, previousPeriodEnd);
        long previousPeriodUsers = userMapper.selectCount(previousWrapper);

        // 计算增长率
        if (previousPeriodUsers == 0) {
            return currentPeriodUsers > 0 ? new BigDecimal("100.00") : BigDecimal.ZERO;
        }

        return BigDecimal.valueOf((double) (currentPeriodUsers - previousPeriodUsers) / previousPeriodUsers * 100)
                .setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimal calculateOrderGrowthRate(int days) {
        LocalDateTime today = LocalDate.now().atStartOfDay();
        LocalDateTime previousPeriodStart = today.minusDays(days * 2L);
        LocalDateTime previousPeriodEnd = today.minusDays(days);

        // 当前时间段订单数
        LambdaQueryWrapper<Order> currentWrapper = new LambdaQueryWrapper<>();
        currentWrapper.ge(Order::getCreateTime, previousPeriodEnd)
                .lt(Order::getCreateTime, today);
        long currentPeriodOrders = orderMapper.selectCount(currentWrapper);

        // 上一时间段订单数
        LambdaQueryWrapper<Order> previousWrapper = new LambdaQueryWrapper<>();
        previousWrapper.ge(Order::getCreateTime, previousPeriodStart)
                .lt(Order::getCreateTime, previousPeriodEnd);
        long previousPeriodOrders = orderMapper.selectCount(previousWrapper);

        // 计算增长率
        if (previousPeriodOrders == 0) {
            return currentPeriodOrders > 0 ? new BigDecimal("100.00") : BigDecimal.ZERO;
        }

        return BigDecimal.valueOf((double) (currentPeriodOrders - previousPeriodOrders) / previousPeriodOrders * 100)
                .setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimal calculateSalesGrowthRate(int days) {
        LocalDateTime today = LocalDate.now().atStartOfDay();
        LocalDateTime previousPeriodStart = today.minusDays(days * 2L);
        LocalDateTime previousPeriodEnd = today.minusDays(days);

        // 当前时间段销售额
        QueryWrapper<Order> currentWrapper = new QueryWrapper<>();
        currentWrapper.select("IFNULL(SUM(pay_amount), 0) as total_amount")
                .ge("create_time", previousPeriodEnd)
                .lt("create_time", today)
                .eq("deleted", 0)
                .gt("status", 0); // 只统计已支付的订单
        Map<String, Object> currentResult = orderMapper.selectMaps(currentWrapper).get(0);
        BigDecimal currentPeriodSales = BigDecimal.valueOf(
                currentResult.get("total_amount") instanceof BigDecimal ?
                        ((BigDecimal) currentResult.get("total_amount")).doubleValue() :
                        Double.parseDouble(currentResult.get("total_amount").toString())
        );

        // 上一时间段销售额
        QueryWrapper<Order> previousWrapper = new QueryWrapper<>();
        previousWrapper.select("IFNULL(SUM(pay_amount), 0) as total_amount")
                .ge("create_time", previousPeriodStart)
                .lt("create_time", previousPeriodEnd)
                .eq("deleted", 0)
                .gt("status", 0); // 只统计已支付的订单
        Map<String, Object> previousResult = orderMapper.selectMaps(previousWrapper).get(0);
        BigDecimal previousPeriodSales = BigDecimal.valueOf(
                previousResult.get("total_amount") instanceof BigDecimal ?
                        ((BigDecimal) previousResult.get("total_amount")).doubleValue() :
                        Double.parseDouble(previousResult.get("total_amount").toString())
        );

        // 计算增长率
        if (previousPeriodSales.compareTo(BigDecimal.ZERO) == 0) {
            return currentPeriodSales.compareTo(BigDecimal.ZERO) > 0 ? new BigDecimal("100.00") : BigDecimal.ZERO;
        }

        return currentPeriodSales.subtract(previousPeriodSales)
                .multiply(new BigDecimal("100"))
                .divide(previousPeriodSales, 2, RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimal calculateMonthlySales() {
        LocalDateTime monthStart = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay();
        LocalDateTime monthEnd = LocalDate.now().plusDays(1).atStartOfDay();

        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.select("IFNULL(SUM(pay_amount), 0) as total_amount")
                .ge("create_time", monthStart)
                .lt("create_time", monthEnd)
                .eq("deleted", 0)
                .gt("status", 0); // 只统计已支付的订单

        Map<String, Object> result = orderMapper.selectMaps(wrapper).get(0);
        return BigDecimal.valueOf(
                result.get("total_amount") instanceof BigDecimal ?
                        ((BigDecimal) result.get("total_amount")).doubleValue() :
                        Double.parseDouble(result.get("total_amount").toString())
        );
    }

    @Override
    public Map<String, Object> getSalesStatistics(String timeRange) {
        Map<String, Object> result = new HashMap<>();
        LocalDateTime startTime;
        LocalDateTime endTime = LocalDate.now().plusDays(1).atStartOfDay();

        // 根据时间范围确定开始时间
        switch (timeRange) {
            case "week":
                startTime = LocalDate.now().minusWeeks(1).atStartOfDay();
                break;
            case "month":
                startTime = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay();
                break;
            case "year":
                startTime = LocalDate.now().with(TemporalAdjusters.firstDayOfYear()).atStartOfDay();
                break;
            default:
                startTime = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay();
        }

        // 查询销售总额
        QueryWrapper<Order> totalWrapper = new QueryWrapper<>();
        totalWrapper.select("IFNULL(SUM(pay_amount), 0) as total_amount")
                .ge("create_time", startTime)
                .lt("create_time", endTime)
                .eq("deleted", 0)
                .gt("status", 0); // 只统计已支付的订单
        Map<String, Object> totalResult = orderMapper.selectMaps(totalWrapper).get(0);
        BigDecimal totalSales = BigDecimal.valueOf(
                totalResult.get("total_amount") instanceof BigDecimal ?
                        ((BigDecimal) totalResult.get("total_amount")).doubleValue() :
                        Double.parseDouble(totalResult.get("total_amount").toString())
        );

        // 查询订单数量
        LambdaQueryWrapper<Order> countWrapper = new LambdaQueryWrapper<>();
        countWrapper.ge(Order::getCreateTime, startTime)
                .lt(Order::getCreateTime, endTime)
                .gt(Order::getStatus, 0); // 只统计已支付的订单
        long orderCount = orderMapper.selectCount(countWrapper);

        // 计算平均订单金额
        BigDecimal averageAmount = orderCount > 0
                ? totalSales.divide(BigDecimal.valueOf(orderCount), 2, RoundingMode.HALF_UP)
                : BigDecimal.ZERO;

        result.put("total", totalSales);
        result.put("count", orderCount);
        result.put("average", averageAmount);

        return result;
    }

    @Override
    public List<Map<String, Object>> getProductSalesRanking(int limit) {
        List<Map<String, Object>> result = new ArrayList<>();

        // 查询每个商品的销售数量和销售额
        QueryWrapper<OrderItem> wrapper = new QueryWrapper<>();
        wrapper.select("product_id, product_name, SUM(quantity) as total_quantity, SUM(total_price) as total_amount")
                .eq("deleted", 0)
                .groupBy("product_id, product_name")
                .orderByDesc("total_quantity")
                .last("LIMIT " + limit);

        List<Map<String, Object>> maps = orderItemMapper.selectMaps(wrapper);
        for (Map<String, Object> map : maps) {
            Map<String, Object> product = new HashMap<>();
            product.put("id", map.get("product_id"));
            product.put("name", map.get("product_name"));
            product.put("sales", Long.valueOf(map.get("total_quantity").toString()));
            product.put("amount", map.get("total_amount"));
            result.add(product);
        }

        return result;
    }

    @Override
    public List<Map<String, Object>> getCategorySalesStatistics() {
        List<Map<String, Object>> result = new ArrayList<>();

        // 获取所有分类
        LambdaQueryWrapper<Category> categoryWrapper = new LambdaQueryWrapper<>();
        categoryWrapper.eq(Category::getStatus, 1);
        List<Category> categories = categoryMapper.selectList(categoryWrapper);

        // 获取所有销售额汇总
        QueryWrapper<OrderItem> totalWrapper = new QueryWrapper<>();
        totalWrapper.select("IFNULL(SUM(total_price), 0) as total_sales")
                .eq("deleted", 0);
        Map<String, Object> totalResult = orderItemMapper.selectMaps(totalWrapper).get(0);
        BigDecimal totalSales = new BigDecimal(totalResult.get("total_sales").toString());

        // 针对每个分类计算销售数据
        for (Category category : categories) {
            // 获取该分类下的所有商品ID
            LambdaQueryWrapper<Product> productWrapper = new LambdaQueryWrapper<>();
            productWrapper.eq(Product::getCategoryId, category.getId())
                    .select(Product::getId);
            List<Product> products = productMapper.selectList(productWrapper);

            if (products.isEmpty()) {
                continue;
            }

            List<Long> productIds = products.stream().map(Product::getId).collect(Collectors.toList());

            // 获取这些商品的销售数量和销售额
            QueryWrapper<OrderItem> itemWrapper = new QueryWrapper<>();
            itemWrapper.select("IFNULL(SUM(quantity), 0) as total_quantity, IFNULL(SUM(total_price), 0) as total_amount")
                    .eq("deleted", 0)
                    .in("product_id", productIds);

            Map<String, Object> salesData = orderItemMapper.selectMaps(itemWrapper).get(0);
            Long sales = Long.valueOf(salesData.get("total_quantity").toString());
            BigDecimal amount = new BigDecimal(salesData.get("total_amount").toString());

            // 计算占比百分比
            BigDecimal percentage = totalSales.compareTo(BigDecimal.ZERO) > 0
                    ? amount.multiply(new BigDecimal("100")).divide(totalSales, 2, RoundingMode.HALF_UP)
                    : BigDecimal.ZERO;

            Map<String, Object> categoryData = new HashMap<>();
            categoryData.put("name", category.getName());
            categoryData.put("sales", sales);
            categoryData.put("amount", amount);
            categoryData.put("percentage", percentage);
            result.add(categoryData);
        }

        // 按销量排序
        result.sort((a, b) -> ((Long) b.get("sales")).compareTo((Long) a.get("sales")));
        return result;
    }

    @Override
    public List<Map<String, Object>> getRecentOrdersWithPagination(int page, int size) {
        List<Map<String, Object>> result = new ArrayList<>();
        int offset = (page - 1) * size;

        // 查询最近订单
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.select("id, order_no, user_id, pay_amount, status, create_time")
                .eq("deleted", 0)
                .orderByDesc("create_time")
                .last("LIMIT " + offset + ", " + size);

        List<Map<String, Object>> orders = orderMapper.selectMaps(wrapper);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        for (Map<String, Object> order : orders) {
            Map<String, Object> orderMap = new HashMap<>();
            orderMap.put("id", order.get("order_no"));
            orderMap.put("userId", order.get("user_id"));

            // 查询用户昵称
            Long userId = Long.valueOf(order.get("user_id").toString());
            User user = userMapper.selectById(userId);
            orderMap.put("userName", user != null ? user.getNickname() : "未知用户");

            orderMap.put("amount", order.get("pay_amount"));
            orderMap.put("status", order.get("status"));

            // 格式化创建时间
            LocalDateTime createTime = (LocalDateTime) order.get("create_time");
            orderMap.put("createTime", createTime.format(formatter));

            result.add(orderMap);
        }

        return result;
    }

    @Override
    public List<Map<String, Object>> getSalesTrend(int days) {
        List<Map<String, Object>> result = new ArrayList<>();
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (int i = 0; i < days; i++) {
            LocalDate date = today.minusDays(i);
            LocalDateTime startTime = date.atStartOfDay();
            LocalDateTime endTime = date.plusDays(1).atStartOfDay();

            // 查询当天销售额
            QueryWrapper<Order> wrapper = new QueryWrapper<>();
            wrapper.select("IFNULL(SUM(pay_amount), 0) as total_sales")
                    .ge("create_time", startTime)
                    .lt("create_time", endTime)
                    .eq("deleted", 0)
                    .gt("status", 0); // 只统计已支付的订单

            Map<String, Object> salesData = orderMapper.selectMaps(wrapper).get(0);
            BigDecimal sales = BigDecimal.valueOf(
                    salesData.get("total_sales") instanceof BigDecimal ?
                            ((BigDecimal) salesData.get("total_sales")).doubleValue() :
                            Double.parseDouble(salesData.get("total_sales").toString())
            );

            Map<String, Object> dayData = new HashMap<>();
            dayData.put("date", date.format(formatter));
            dayData.put("sales", sales);
            result.add(dayData);
        }

        // 按日期排序
        result.sort(Comparator.comparing(m -> (String) m.get("date")));
        return result;
    }

    @Override
    public long countNewReviews(int days) {
        LocalDateTime startTime = LocalDate.now().minusDays(days).atStartOfDay();

        // 查询新增评价数量
        LambdaQueryWrapper<com.example.model.entity.Review> wrapper = new LambdaQueryWrapper<>();
        wrapper.ge(com.example.model.entity.Review::getCreateTime, startTime);

        return reviewMapper.selectCount(wrapper);
    }
} 