package com.example.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.annotation.RequiresRole;
import com.example.common.PageResult;
import com.example.common.Result;
import com.example.common.ResultCode;
import com.example.model.dto.UserQueryDTO;
import com.example.model.entity.Product;
import com.example.model.entity.User;
import com.example.model.vo.OrderVO;
import com.example.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 管理员控制器
 * 需要管理员角色才能访问
 */
@Slf4j
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@RequiresRole(1) // 管理员角色值为1
public class AdminController {

    private final UserService userService;
    private final OrderService orderService;
    private final ProductService productService;
    private final StatisticsService statisticsService;

    /**
     * 获取用户列表（分页）
     *
     * @param page     页码
     * @param size     每页数量
     * @param queryDTO 查询条件
     * @return 用户分页数据
     */
    @GetMapping("/users")
    public Result<PageResult<User>> getUserList(
            @RequestParam(defaultValue = "1") long page,
            @RequestParam(defaultValue = "10") long size,
            UserQueryDTO queryDTO) {

        Page<User> userPage = userService.getUserPage(page, size, queryDTO);

        // 转换为自定义PageResult
        PageResult<User> pageResult = new PageResult<>(
                userPage.getCurrent(),
                userPage.getSize(),
                userPage.getTotal(),
                userPage.getRecords()
        );

        // 清除密码等敏感信息
        pageResult.getRecords().forEach(user -> user.setPassword(null));

        return Result.success(pageResult);
    }

    /**
     * 获取用户详情
     *
     * @param userId 用户ID
     * @return 用户详情
     */
    @GetMapping("/users/{userId}")
    public Result<User> getUserDetail(@PathVariable Long userId) {
        User user = userService.getById(userId);
        if (user != null) {
            user.setPassword(null); // 不返回密码
        }
        return Result.success(user);
    }

    /**
     * 添加用户
     *
     * @param user 用户信息
     * @return 添加结果
     */
    @PostMapping("/users")
    public Result<Boolean> addUser(@RequestBody @Valid User user) {
        boolean result = userService.addUser(user);
        return Result.success(result, "添加用户成功");
    }

    /**
     * 更新用户
     *
     * @param user 用户信息
     * @return 更新结果
     */
    @PutMapping("/users")
    public Result<Boolean> updateUser(@RequestBody @Valid User user) {
        if (user.getId() == null) {
            return Result.error("用户ID不能为空");
        }
        boolean result = userService.updateUser(user);
        return Result.success(result, "更新用户成功");
    }

    /**
     * 删除用户
     *
     * @param userId 用户ID
     * @return 删除结果
     */
    @DeleteMapping("/users/{userId}")
    public Result<Boolean> deleteUser(@PathVariable Long userId) {
        boolean result = userService.deleteUser(userId);
        return Result.success(result, "删除用户成功");
    }

    /**
     * 启用/禁用用户
     *
     * @param userId 用户ID
     * @param status 状态：0-禁用，1-正常
     * @return 操作结果
     */
    @PutMapping("/users/{userId}/status")
    public Result<Boolean> updateUserStatus(
            @PathVariable Long userId,
            @RequestParam Integer status) {

        boolean result = userService.updateUserStatus(userId, status);
        String statusText = status == 1 ? "启用" : "禁用";
        return Result.success(result, "用户" + statusText + "成功");
    }

    /**
     * 重置用户密码
     *
     * @param userId   用户ID
     * @param password 新密码
     * @return 重置结果
     */
    @PutMapping("/users/{userId}/password")
    public Result<Boolean> resetPassword(
            @PathVariable Long userId,
            @RequestParam String password) {

        boolean result = userService.resetPassword(userId, password);
        return Result.success(result, "密码重置成功");
    }

    /**
     * 修改用户角色
     *
     * @param userId 用户ID
     * @param role   角色：1-管理员，2-用户
     * @return 操作结果
     */
    @PutMapping("/users/{userId}/role")
    public Result<Boolean> updateUserRole(
            @PathVariable Long userId,
            @RequestParam Integer role) {

        boolean result = userService.updateUserRole(userId, role);
        String roleText = role == 1 ? "管理员" : "普通用户";
        return Result.success(result, "用户角色已修改为" + roleText);
    }

    /**
     * 获取用户统计信息
     *
     * @return 用户统计信息
     */
    @GetMapping("/users/statistics")
    public Result<Map<String, Object>> getUserStatistics() {
        Map<String, Object> statistics = new HashMap<>();

        // 用户总数
        long totalUsers = userService.count();
        statistics.put("totalUsers", totalUsers);

        // 今日新增用户
        long todayNewUsers = userService.countTodayNewUsers();
        statistics.put("todayNewUsers", todayNewUsers);

        // 活跃用户数（30天内有登录记录）
        long activeUsers = userService.countActiveUsers(30);
        statistics.put("activeUsers", activeUsers);

        // 用户性别分布
        Map<Integer, Long> genderDistribution = userService.getGenderDistribution();
        statistics.put("genderDistribution", genderDistribution);

        // 每日注册用户数（近7天）
        List<Map<String, Object>> dailyRegistrations = statisticsService.getDailyUserRegistrations(7);
        statistics.put("dailyRegistrations", dailyRegistrations);

        return Result.success(statistics);
    }

    /**
     * 获取订单列表（分页）
     *
     * @param page      页码
     * @param size      每页数量
     * @param status    订单状态（可选）
     * @param orderNo   订单编号（可选）
     * @param startTime 开始时间（可选）
     * @param endTime   结束时间（可选）
     * @return 订单分页数据
     */
    @GetMapping("/orders")
    public Result<PageResult<OrderVO>> getOrderList(
            @RequestParam(defaultValue = "1") long page,
            @RequestParam(defaultValue = "10") long size,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) String orderNo,
            @RequestParam(required = false) String startTime,
            @RequestParam(required = false) String endTime) {

        Page<OrderVO> orderPage = orderService.getOrderPage(page, size, status, orderNo, startTime, endTime);

        // 转换为自定义PageResult
        PageResult<OrderVO> pageResult = new PageResult<>(
                orderPage.getCurrent(),
                orderPage.getSize(),
                orderPage.getTotal(),
                orderPage.getRecords()
        );

        return Result.success(pageResult);
    }

    /**
     * 获取订单详情
     *
     * @param orderNo 订单编号
     * @return 订单详情
     */
    @GetMapping("/orders/{orderNo}")
    public Result<OrderVO> getOrderDetail(@PathVariable String orderNo) {
        OrderVO orderVO = orderService.getOrderDetail(null, orderNo);
        return Result.success(orderVO);
    }

    /**
     * 订单发货
     *
     * @param orderNo      订单编号
     * @param shippingCode 物流单号
     * @return 发货结果
     */
    @PostMapping("/orders/{orderNo}/ship")
    public Result<Boolean> shipOrder(@PathVariable String orderNo, @RequestParam String shippingCode) {
        // 验证参数
        if (!StringUtils.hasText(orderNo) || !StringUtils.hasText(shippingCode)) {
            return Result.error("订单编号或物流单号不能为空", ResultCode.PARAM_ERROR);
        }

        // 调用订单服务发货
        boolean result = orderService.ship(orderNo, shippingCode);

        if (result) {
            return Result.success(true, "发货成功");
        } else {
            return Result.error("发货失败");
        }
    }

    /**
     * 取消订单
     *
     * @param orderNo 订单编号
     * @return 取消结果
     */
    @PutMapping("/orders/{orderNo}/cancel")
    public Result<Boolean> cancelOrder(@PathVariable String orderNo) {
        boolean result = orderService.cancelByAdmin(orderNo);
        return Result.success(result, "订单取消成功");
    }

    /**
     * 获取订单统计信息
     *
     * @return 订单统计信息
     */
    @GetMapping("/orders/statistics")
    public Result<Map<String, Object>> getOrderStatistics() {
        try {
            // 直接调用OrderService中的getOrderStatistics方法，该方法已包含异常处理
            Map<String, Object> statistics = orderService.getOrderStatistics();
            return Result.success(statistics);
        } catch (Exception e) {
            log.error("获取订单统计信息失败", e);
            // 出错时返回一个空的统计对象，避免前端报错
            Map<String, Object> emptyStats = new HashMap<>();
            emptyStats.put("totalOrders", 0L);
            emptyStats.put("todayOrders", 0L);
            emptyStats.put("totalSales", BigDecimal.ZERO);
            emptyStats.put("todaySales", BigDecimal.ZERO);
            emptyStats.put("totalProducts", 0L);
            emptyStats.put("soldProducts", 0L);
            emptyStats.put("pendingOrders", 0L);
            emptyStats.put("lowStockProducts", 0L);
            emptyStats.put("newReviews", 0L);
            emptyStats.put("dailyOrders", new ArrayList<>());

            return Result.success(emptyStats);
        }
    }

    /**
     * 获取订单状态分布
     *
     * @return 状态分布数据
     */
    @GetMapping("/orders/status-distribution")
    public Result<List<Map<String, Object>>> getOrderStatusDistribution() {
        List<Map<String, Object>> distribution = new ArrayList<>();

        try {
            // 获取各状态订单数量
            for (int status = 0; status <= 4; status++) {
                Map<String, Object> statusData = new HashMap<>();
                try {
                    long count = orderService.countByStatus(status);
                    String statusName = orderService.getOrderStatusDesc(status);
                    statusData.put("status", status);
                    statusData.put("statusName", statusName);
                    statusData.put("count", count);
                } catch (Exception e) {
                    log.error("获取订单状态{}的数量失败", status, e);
                    statusData.put("status", status);
                    statusData.put("statusName", getDefaultStatusDesc(status));
                    statusData.put("count", 0L);
                }
                distribution.add(statusData);
            }
        } catch (Exception e) {
            log.error("获取订单状态分布失败", e);
            // 如果出错，提供默认数据
            String[] statusNames = {"已取消", "待发货", "已发货", "已完成", "已关闭"};
            for (int status = 0; status <= 4; status++) {
                distribution.add(Map.of(
                        "status", status,
                        "statusName", statusNames[status],
                        "count", 0L
                ));
            }
        }

        return Result.success(distribution);
    }

    /**
     * 获取默认状态描述，用于异常情况
     */
    private String getDefaultStatusDesc(int status) {
        switch (status) {
            case 0:
                return "已取消";
            case 1:
                return "待发货";
            case 2:
                return "已发货";
            case 3:
                return "已完成";
            case 4:
                return "已关闭";
            default:
                return "未知状态";
        }
    }

    /**
     * 修改订单备注
     *
     * @param orderNo 订单编号
     * @param note    备注内容
     * @return 修改结果
     */
    @PutMapping("/orders/{orderNo}/note")
    public Result<Boolean> updateOrderNote(
            @PathVariable String orderNo,
            @RequestParam String note) {

        boolean result = orderService.updateOrderNote(orderNo, note);
        return Result.success(result, "修改备注成功");
    }

    /**
     * 导出订单数据（仅返回下载链接，实际导出需要另外处理）
     *
     * @param status    订单状态（可选）
     * @param startTime 开始时间（可选）
     * @param endTime   结束时间（可选）
     * @return 导出结果
     */
    @GetMapping("/orders/export")
    public Result<String> exportOrders(
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) String startTime,
            @RequestParam(required = false) String endTime) {

        // 这里简化处理，实际项目中应该生成Excel文件并返回下载链接
        return Result.success("/download/orders.xlsx", "订单数据导出成功");
    }

    /**
     * 获取商品列表（分页）
     *
     * @param page       页码
     * @param size       每页数量
     * @param categoryId 分类ID（可选）
     * @param keyword    关键词（可选）
     * @param minPrice   最小价格（可选）
     * @param maxPrice   最大价格（可选）
     * @param status     商品状态（可选）：0-下架，1-上架
     * @return 商品分页数据
     */
    @GetMapping("/products")
    public Result<PageResult<Product>> getProductList(
            @RequestParam(defaultValue = "1") long page,
            @RequestParam(defaultValue = "10") long size,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Integer status) {

        // 分页查询商品
        Page<Product> productPage = productService.getProductPage(page, size, categoryId, keyword, minPrice, maxPrice);

        // 转换为自定义PageResult
        PageResult<Product> pageResult = new PageResult<>(
                productPage.getCurrent(),
                productPage.getSize(),
                productPage.getTotal(),
                productPage.getRecords()
        );

        return Result.success(pageResult);
    }

    /**
     * 获取商品详情
     *
     * @param productId 商品ID
     * @return 商品详情
     */
    @GetMapping("/products/{productId}")
    public Result<Product> getProductDetail(@PathVariable Long productId) {
        Product product = productService.getById(productId);
        if (product == null) {
            return Result.error("商品不存在", ResultCode.NOT_FOUND);
        }
        return Result.success(product);
    }

    /**
     * 添加商品
     *
     * @param product 商品信息
     * @return 添加结果
     */
    @PostMapping("/products")
    public Result<Boolean> addProduct(@RequestBody @Valid Product product) {
        // 设置默认值
        if (product.getStatus() == null) {
            product.setStatus(0); // 默认下架状态
        }
        if (product.getSales() == null) {
            product.setSales(0); // 默认销量为0
        }

        boolean result = productService.save(product);
        return Result.success(result, "添加商品成功");
    }

    /**
     * 更新商品
     *
     * @param product 商品信息
     * @return 更新结果
     */
    @PutMapping("/products")
    public Result<Boolean> updateProduct(@RequestBody @Valid Product product) {
        if (product.getId() == null) {
            return Result.error("商品ID不能为空", ResultCode.PARAM_ERROR);
        }

        boolean result = productService.updateById(product);
        return Result.success(result, "更新商品成功");
    }

    /**
     * 删除商品（逻辑删除）
     *
     * @param productId 商品ID
     * @return 删除结果
     */
    @DeleteMapping("/products/{productId}")
    public Result<Boolean> deleteProduct(@PathVariable Long productId) {
        boolean result = productService.removeById(productId);
        return Result.success(result, "删除商品成功");
    }

    /**
     * 商品上下架
     *
     * @param productId 商品ID
     * @param status    状态：0-下架，1-上架
     * @return 操作结果
     */
    @PutMapping("/products/{productId}/status")
    public Result<Boolean> updateProductStatus(
            @PathVariable Long productId,
            @RequestParam Integer status) {

        if (status != 0 && status != 1) {
            return Result.error("状态值不合法", ResultCode.PARAM_ERROR);
        }

        Product product = new Product();
        product.setId(productId);
        product.setStatus(status);

        boolean result = productService.updateById(product);
        String statusText = status == 1 ? "上架" : "下架";
        return Result.success(result, "商品" + statusText + "成功");
    }

    /**
     * 更新商品库存
     *
     * @param productId 商品ID
     * @param stock     库存数量
     * @return 操作结果
     */
    @PutMapping("/products/{productId}/stock")
    public Result<Boolean> updateProductStock(
            @PathVariable Long productId,
            @RequestParam Integer stock) {

        if (stock < 0) {
            return Result.error("库存不能小于0", ResultCode.PARAM_ERROR);
        }

        Product product = new Product();
        product.setId(productId);
        product.setStock(stock);

        boolean result = productService.updateById(product);
        return Result.success(result, "更新库存成功");
    }

    /**
     * 更新商品价格
     *
     * @param productId     商品ID
     * @param price         价格
     * @param originalPrice 原价（可选）
     * @return 操作结果
     */
    @PutMapping("/products/{productId}/price")
    public Result<Boolean> updateProductPrice(
            @PathVariable Long productId,
            @RequestParam Double price,
            @RequestParam(required = false) Double originalPrice) {

        if (price < 0) {
            return Result.error("价格不能小于0", ResultCode.PARAM_ERROR);
        }

        Product product = new Product();
        product.setId(productId);
        product.setPrice(new BigDecimal(price));

        if (originalPrice != null && originalPrice >= 0) {
            product.setOriginalPrice(new BigDecimal(originalPrice));
        }

        boolean result = productService.updateById(product);
        return Result.success(result, "更新价格成功");
    }

    /**
     * 上传商品主图
     *
     * @param file      图片文件
     * @param productId 商品ID
     * @return 图片URL
     */
    @PostMapping("/products/image")
    public Result<String> uploadProductImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam("productId") Long productId) {

        if (file.isEmpty()) {
            return Result.error("请选择图片文件", ResultCode.PARAM_ERROR);
        }

        String imageUrl = productService.uploadImage(file, productId);
        return Result.success(imageUrl, "上传商品主图成功");
    }

    /**
     * 上传商品图片（添加到图片集）
     *
     * @param file      图片文件
     * @param productId 商品ID
     * @return 图片URL
     */
    @PostMapping("/products/images")
    public Result<String> uploadProductImages(
            @RequestParam("file") MultipartFile file,
            @RequestParam("productId") Long productId) {

        if (file.isEmpty()) {
            return Result.error("请选择图片文件", ResultCode.PARAM_ERROR);
        }

        String imageUrl = productService.uploadProductImage(file, productId);
        return Result.success(imageUrl, "上传商品图片成功");
    }

    /**
     * 删除商品图片
     *
     * @param productId 商品ID
     * @param imageUrl  图片URL
     * @return 操作结果
     */
    @DeleteMapping("/products/{productId}/images")
    public Result<Boolean> deleteProductImage(
            @PathVariable Long productId,
            @RequestParam String imageUrl) {

        boolean result = productService.deleteProductImage(productId, imageUrl);
        return Result.success(result, "删除商品图片成功");
    }

    /**
     * 批量上下架商品
     *
     * @param productIds 商品ID列表
     * @param status     状态：0-下架，1-上架
     * @return 操作结果
     */
    @PutMapping("/products/batch/status")
    public Result<Boolean> batchUpdateProductStatus(
            @RequestParam List<Long> productIds,
            @RequestParam Integer status) {

        if (productIds == null || productIds.isEmpty()) {
            return Result.error("商品ID列表不能为空", ResultCode.PARAM_ERROR);
        }

        if (status != 0 && status != 1) {
            return Result.error("状态值不合法", ResultCode.PARAM_ERROR);
        }

        boolean result = productService.batchUpdateProductStatus(productIds, status);

        String statusText = status == 1 ? "上架" : "下架";
        return Result.success(result, "批量" + statusText + "商品成功");
    }

    /**
     * 获取销售额统计（按时间段）
     *
     * @param timeRange 时间范围：week-本周，month-本月，year-本年
     * @return 销售额统计
     */
    @GetMapping("/statistics/sales")
    public Result<Map<String, Object>> getSalesStatistics(@RequestParam(defaultValue = "week") String timeRange) {
        Map<String, Object> statistics = statisticsService.getSalesStatistics(timeRange);
        return Result.success(statistics);
    }

    /**
     * 获取商品销售排行榜
     *
     * @param limit 限制数量
     * @return 商品销售排行榜
     */
    @GetMapping("/statistics/product-ranking")
    public Result<List<Map<String, Object>>> getProductRanking(@RequestParam(defaultValue = "10") int limit) {
        List<Map<String, Object>> ranking = statisticsService.getProductSalesRanking(limit);
        return Result.success(ranking);
    }

    /**
     * 获取分类销售统计
     *
     * @return 分类销售统计
     */
    @GetMapping("/statistics/category-sales")
    public Result<List<Map<String, Object>>> getCategorySalesStatistics() {
        List<Map<String, Object>> statistics = statisticsService.getCategorySalesStatistics();
        return Result.success(statistics);
    }

    /**
     * 获取仪表盘综合数据
     *
     * @return 仪表盘数据
     */
    @GetMapping("/dashboard/summary")
    public Result<Map<String, Object>> getDashboardSummary() {
        Map<String, Object> summary = new HashMap<>();

        try {
            // 用户数据
            Map<String, Object> userData = new HashMap<>();
            userData.put("total", userService.count());
            userData.put("today", userService.countTodayNewUsers());
            userData.put("active", userService.countActiveUsers(30));

            try {
                userData.put("growth", statisticsService.calculateUserGrowthRate(30)); // 30天增长率
            } catch (Exception e) {
                log.error("计算用户增长率失败", e);
                userData.put("growth", BigDecimal.ZERO);
            }

            // 订单数据
            Map<String, Object> orderData = new HashMap<>();
            orderData.put("total", orderService.count());
            orderData.put("today", orderService.countTodayOrders());
            orderData.put("pending", orderService.countByStatus(1)); // 待发货

            try {
                orderData.put("growth", statisticsService.calculateOrderGrowthRate(30)); // 30天增长率
            } catch (Exception e) {
                log.error("计算订单增长率失败", e);
                orderData.put("growth", BigDecimal.ZERO);
            }

            // 销售数据
            Map<String, Object> salesData = new HashMap<>();
            salesData.put("total", orderService.calculateTotalSales());
            salesData.put("today", orderService.calculateTodaySales());

            try {
                salesData.put("monthly", statisticsService.calculateMonthlySales());
                salesData.put("growth", statisticsService.calculateSalesGrowthRate(30)); // 30天增长率
            } catch (Exception e) {
                log.error("计算销售相关统计失败", e);
                salesData.put("monthly", BigDecimal.ZERO);
                salesData.put("growth", BigDecimal.ZERO);
            }

            // 商品数据
            Map<String, Object> productData = new HashMap<>();
            productData.put("total", productService.count());
            productData.put("active", productService.countActiveProducts());
            productData.put("lowStock", productService.countLowStockProducts(10));

            summary.put("user", userData);
            summary.put("order", orderData);
            summary.put("sales", salesData);
            summary.put("product", productData);

            // 近期趋势数据
            try {
                summary.put("recentOrders", statisticsService.getRecentOrdersWithPagination(1, 5));
                summary.put("salesTrend", statisticsService.getSalesTrend(7)); // 7天销售趋势
            } catch (Exception e) {
                log.error("获取趋势数据失败", e);
                summary.put("recentOrders", new ArrayList<>());
                summary.put("salesTrend", new ArrayList<>());
            }
        } catch (Exception e) {
            log.error("获取仪表盘数据失败", e);
            // 提供默认数据，确保前端不会崩溃
            summary.put("user", Map.of(
                    "total", 0L,
                    "today", 0L,
                    "active", 0L,
                    "growth", BigDecimal.ZERO
            ));

            summary.put("order", Map.of(
                    "total", 0L,
                    "today", 0L,
                    "pending", 0L,
                    "growth", BigDecimal.ZERO
            ));

            summary.put("sales", Map.of(
                    "total", BigDecimal.ZERO,
                    "today", BigDecimal.ZERO,
                    "monthly", BigDecimal.ZERO,
                    "growth", BigDecimal.ZERO
            ));

            summary.put("product", Map.of(
                    "total", 0L,
                    "active", 0L,
                    "lowStock", 0L
            ));

            summary.put("recentOrders", new ArrayList<>());
            summary.put("salesTrend", new ArrayList<>());
        }

        return Result.success(summary);
    }
} 