package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.ResultCode;
import com.example.exception.BusinessException;
import com.example.mapper.OrderMapper;
import com.example.model.dto.OrderCreateDTO;
import com.example.model.entity.Address;
import com.example.model.entity.Order;
import com.example.model.vo.CartVO;
import com.example.model.vo.OrderItemVO;
import com.example.model.vo.OrderVO;
import com.example.service.*;
import com.example.util.OrderNoUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 28619
 * @description 针对表【order(订单表)】的数据库操作Service实现
 * @createDate 2025-04-09 17:42:27
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order>
        implements OrderService {

    // 订单状态常量
    private static final int STATUS_NOT_PAY = 0;    // 待付款
    private static final int STATUS_PAID = 1;      // 待发货
    private static final int STATUS_SHIPPED = 2;   // 待收货
    private static final int STATUS_COMPLETED = 3; // 已完成
    private static final int STATUS_CANCELED = 4;  // 已取消
    // 支付方式常量
    private static final int PAY_TYPE_ALIPAY = 1;  // 支付宝
    private static final int PAY_TYPE_WECHAT = 2;  // 微信支付
    // 订单操作常量
    private static final String ACTION_PAY = "pay";           // 支付
    private static final String ACTION_CANCEL = "cancel";     // 取消
    private static final String ACTION_CONFIRM = "confirm";   // 确认收货
    private static final String ACTION_DELETE = "delete";     // 删除
    private final CartService cartService;
    private final OrderItemService orderItemService;
    private final AddressService addressService;
    private final ProductService productService;
    private final StatisticsService statisticsService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "order", key = "'user:' + #userId + ':orders'")
    public String create(Long userId, OrderCreateDTO orderCreateDTO) {
        if (userId == null || orderCreateDTO == null) {
            throw new BusinessException("参数错误", ResultCode.PARAM_ERROR);
        }

        // 生成订单号，使用OrderNoUtil工具类
        String orderNo = OrderNoUtil.generateOrderNo();

        // 根据地址ID获取收货地址信息
        Address address = addressService.getById(orderCreateDTO.getAddressId());
        if (address == null) {
            throw new BusinessException("收货地址不存在", ResultCode.PARAM_ERROR);
        }

        // 判断是从购物车创建订单还是直接购买
        BigDecimal totalAmount;
        if (orderCreateDTO.getFromCart()) {
            // 从购物车创建订单
            CartVO cartVO = cartService.getCartByUserId(userId);
            if (cartVO.getCartItems().isEmpty() || cartVO.getSelectedCount() == 0) {
                throw new BusinessException("购物车中无选中商品", ResultCode.PARAM_ERROR);
            }

            // 创建订单对象
            Order order = buildOrder(userId, orderNo, orderCreateDTO, address);

            // 保存订单
            save(order);

            // 获取选中的购物车项
            List<com.example.model.vo.CartItemVO> selectedItems = cartVO.getCartItems().stream()
                    .filter(item -> item.getChecked() == 1)
                    .collect(Collectors.toList());

            // 保存订单商品并获取总金额
            totalAmount = orderItemService.saveOrderItems(orderNo, order.getId(), selectedItems);

            // 更新订单金额
            order.setTotalAmount(totalAmount);

            // 计算实付金额（总金额 + 运费）
            BigDecimal payAmount = totalAmount;
            if (orderCreateDTO.getFreightAmount() != null) {
                payAmount = totalAmount.add(orderCreateDTO.getFreightAmount());
                order.setFreightAmount(orderCreateDTO.getFreightAmount());
            }
            order.setPayAmount(payAmount);

            // 更新订单
            updateById(order);

            // 清空购物车中已下单的商品
            cartService.removeBatch(userId, selectedItems.stream()
                    .map(com.example.model.vo.CartItemVO::getProductId).toArray(Long[]::new));

        } else {
            // 直接购买商品
            if (orderCreateDTO.getProductId() == null || orderCreateDTO.getQuantity() == null) {
                throw new BusinessException("商品信息不完整", ResultCode.PARAM_ERROR);
            }

            // 创建订单对象
            Order order = buildOrder(userId, orderNo, orderCreateDTO, address);

            // 保存订单
            save(order);

            // 保存订单商品并获取总金额
            totalAmount = orderItemService.saveOrderItem(orderNo, order.getId(),
                    orderCreateDTO.getProductId(), orderCreateDTO.getQuantity());

            // 更新订单金额
            order.setTotalAmount(totalAmount);

            // 计算实付金额（总金额 + 运费）
            BigDecimal payAmount = totalAmount;
            if (orderCreateDTO.getFreightAmount() != null) {
                payAmount = totalAmount.add(orderCreateDTO.getFreightAmount());
                order.setFreightAmount(orderCreateDTO.getFreightAmount());
            }
            order.setPayAmount(payAmount);

            // 更新订单
            updateById(order);

        }

        return orderNo;
    }

    @Override
    @Cacheable(value = "order", key = "'orderNo:' + #orderNo")
    public OrderVO getOrderDetail(Long userId, String orderNo) {
        if (orderNo == null) {
            throw new BusinessException("订单编号不能为空", ResultCode.PARAM_ERROR);
        }

        // 查询订单
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();
        // 当userId不为null时，按用户ID筛选；否则只按订单号查询（管理员查询）
        if (userId != null) {
            queryWrapper.eq(Order::getUserId, userId);
        }
        queryWrapper.eq(Order::getOrderNo, orderNo);

        Order order = getOne(queryWrapper);
        if (order == null) {
            throw new BusinessException("订单不存在", ResultCode.NOT_FOUND);
        }

        // 查询订单商品
        List<OrderItemVO> orderItems = orderItemService.getOrderItemsByOrderId(order.getId());

        // 组装订单VO
        OrderVO orderVO = convertToOrderVO(order);
        orderVO.setOrderItems(orderItems);

        return orderVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "order", key = "'orderNo:' + #orderNo")
    public boolean cancel(Long userId, String orderNo) {
        if (userId == null || orderNo == null) {
            throw new BusinessException("参数错误", ResultCode.PARAM_ERROR);
        }

        // 查询订单
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Order::getUserId, userId)
                .eq(Order::getOrderNo, orderNo);

        Order order = getOne(queryWrapper);
        if (order == null) {
            throw new BusinessException("订单不存在", ResultCode.NOT_FOUND);
        }

        // 检查订单状态，只有待付款的订单才能取消
        if (order.getStatus() != STATUS_NOT_PAY) {
            throw new BusinessException("订单无法取消", ResultCode.PARAM_ERROR);
        }

        // 更新订单状态为已取消
        order.setStatus(STATUS_CANCELED);
        boolean result = updateById(order);

        if (result) {
            // 恢复商品库存和销量
            List<OrderItemVO> orderItems = orderItemService.getOrderItemsByOrderId(order.getId());
            for (OrderItemVO orderItem : orderItems) {
                // 减少销量
                productService.increaseSales(orderItem.getProductId(), -orderItem.getQuantity());

                // 增加库存
                // 由于ProductService没有直接的增加库存方法，我们可以使用decreaseStock的反向操作
                // 这里我们假设decreaseStock方法允许传入负数来增加库存
                productService.decreaseStock(orderItem.getProductId(), -orderItem.getQuantity());
            }
        }

        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "order", key = "'orderNo:' + #orderNo")
    public Map<String, String> pay(Long userId, String orderNo, Integer payType) {
        if (userId == null || orderNo == null || payType == null) {
            throw new BusinessException("参数错误", ResultCode.PARAM_ERROR);
        }

        // 查询订单
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Order::getUserId, userId)
                .eq(Order::getOrderNo, orderNo);

        Order order = getOne(queryWrapper);
        if (order == null) {
            throw new BusinessException("订单不存在", ResultCode.NOT_FOUND);
        }

        // 检查订单状态，只有待付款的订单才能支付
        if (order.getStatus() != STATUS_NOT_PAY) {
            throw new BusinessException("订单无法支付", ResultCode.PARAM_ERROR);
        }

        // 更新订单支付方式
        order.setPayType(payType);
        updateById(order);

        // 返回支付参数，实际项目中这里应该调用支付接口获取支付参数
        // 这里简化处理，仅返回一些模拟数据
        Map<String, String> result = new HashMap<>();
        result.put("orderNo", orderNo);
        result.put("payAmount", order.getPayAmount().toString());

        if (payType == PAY_TYPE_ALIPAY) {
            result.put("payType", "alipay");
            result.put("payInfo", "alipay://pay?orderNo=" + orderNo);
        } else if (payType == PAY_TYPE_WECHAT) {
            result.put("payType", "wechat");
            result.put("payInfo", "weixin://pay?orderNo=" + orderNo);
        } else {
            throw new BusinessException("不支持的支付方式", ResultCode.PARAM_ERROR);
        }

        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "order", key = "'orderNo:' + #orderNo")
    public boolean payCallback(String orderNo, String tradeNo) {
        if (orderNo == null) {
            return false;
        }

        // 查询订单
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Order::getOrderNo, orderNo);

        Order order = getOne(queryWrapper);
        if (order == null || order.getStatus() != STATUS_NOT_PAY) {
            return false;
        }

        // 更新订单状态为待发货
        order.setStatus(STATUS_PAID);
        // 记录支付交易号等信息（这里可以根据实际需求扩展）


        return updateById(order);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "order", key = "'orderNo:' + #orderNo")
    public boolean confirm(Long userId, String orderNo) {
        if (userId == null || orderNo == null) {
            throw new BusinessException("参数错误", ResultCode.PARAM_ERROR);
        }

        // 查询订单
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Order::getUserId, userId)
                .eq(Order::getOrderNo, orderNo);

        Order order = getOne(queryWrapper);
        if (order == null) {
            throw new BusinessException("订单不存在", ResultCode.NOT_FOUND);
        }

        // 检查订单状态，只有待收货的订单才能确认收货
        if (order.getStatus() != STATUS_SHIPPED) {
            throw new BusinessException("订单无法确认收货", ResultCode.PARAM_ERROR);
        }

        // 更新订单状态为已完成
        order.setStatus(STATUS_COMPLETED);

        return updateById(order);
    }

    @Override
    @Cacheable(value = "order", key = "'user:' + #userId + ':orders:status:' + #status + ':page:' + #page + ':size:' + #size")
    public Page<OrderVO> getUserOrders(Long userId, Integer status, long page, long size) {
        if (userId == null) {
            throw new BusinessException("参数错误", ResultCode.PARAM_ERROR);
        }

        // 构建查询条件
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Order::getUserId, userId);

        // 如果指定了状态，则按状态筛选
        if (status != null) {
            queryWrapper.eq(Order::getStatus, status);
        }

        // 按创建时间倒序排序
        queryWrapper.orderByDesc(Order::getCreateTime);

        // 执行分页查询
        Page<Order> orderPage = page(new Page<>(page, size), queryWrapper);

        // 转换为OrderVO
        List<OrderVO> orderVOList = new ArrayList<>();
        for (Order order : orderPage.getRecords()) {
            OrderVO orderVO = convertToOrderVO(order);

            // 获取订单商品
            List<OrderItemVO> orderItems = orderItemService.getOrderItemsByOrderId(order.getId());
            orderVO.setOrderItems(orderItems);

            orderVOList.add(orderVO);
        }

        // 构建OrderVO分页结果
        Page<OrderVO> orderVOPage = new Page<>(orderPage.getCurrent(), orderPage.getSize(), orderPage.getTotal());
        orderVOPage.setRecords(orderVOList);

        return orderVOPage;
    }

    @Override
    @Cacheable(value = "order", key = "'order:status:desc:' + #status")
    public String getOrderStatusDesc(Integer status) {
        if (status == null) {
            return "未知状态";
        }

        switch (status) {
            case STATUS_NOT_PAY:
                return "待付款";
            case STATUS_PAID:
                return "待发货";
            case STATUS_SHIPPED:
                return "待收货";
            case STATUS_COMPLETED:
                return "已完成";
            case STATUS_CANCELED:
                return "已取消";
            default:
                return "未知状态";
        }
    }

    @Override
    @Cacheable(value = "order", key = "'order:payType:desc:' + #payType")
    public String getPayTypeDesc(Integer payType) {
        if (payType == null) {
            return "未支付";
        }

        switch (payType) {
            case PAY_TYPE_ALIPAY:
                return "支付宝";
            case PAY_TYPE_WECHAT:
                return "微信支付";
            default:
                return "其他支付方式";
        }
    }

    @Override
    @Cacheable(value = "order", key = "'order:actions:' + #orderStatus")
    public List<String> getAllowActions(Integer orderStatus) {
        List<String> actions = new ArrayList<>();

        if (orderStatus == null) {
            return actions;
        }

        switch (orderStatus) {
            case STATUS_NOT_PAY:
                actions.add(ACTION_PAY);
                actions.add(ACTION_CANCEL);
                break;
            case STATUS_PAID:
                break;  // 待发货状态，用户无可执行操作
            case STATUS_SHIPPED:
                actions.add(ACTION_CONFIRM);
                break;
            case STATUS_COMPLETED:
                actions.add(ACTION_DELETE);
                break;
            case STATUS_CANCELED:
                actions.add(ACTION_DELETE);
                break;
        }

        return actions;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "order", key = "'orderNo:' + #orderNo")
    public boolean deleteOrder(Long userId, String orderNo) {
        if (userId == null || orderNo == null) {
            throw new BusinessException("参数错误", ResultCode.PARAM_ERROR);
        }

        // 查询订单
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Order::getUserId, userId)
                .eq(Order::getOrderNo, orderNo);

        Order order = getOne(queryWrapper);
        if (order == null) {
            throw new BusinessException("订单不存在", ResultCode.NOT_FOUND);
        }

        // 检查订单状态，只有已完成或已取消的订单才能删除
        if (order.getStatus() != STATUS_COMPLETED && order.getStatus() != STATUS_CANCELED) {
            throw new BusinessException("订单无法删除", ResultCode.PARAM_ERROR);
        }

        // 删除订单（逻辑删除）
        boolean result = removeById(order.getId());

        if (result) {
            // 删除订单商品（逻辑删除）
            orderItemService.deleteByOrderId(order.getId());
        }

        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "order", key = "'orderNo:' + #orderNo")
    public boolean ship(String orderNo, String shipmentNumber) {
        if (orderNo == null) {
            throw new BusinessException("订单编号不能为空", ResultCode.PARAM_ERROR);
        }

        // 查询订单
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Order::getOrderNo, orderNo);

        Order order = getOne(queryWrapper);
        if (order == null) {
            throw new BusinessException("订单不存在", ResultCode.NOT_FOUND);
        }

        // 检查订单状态，只有待发货的订单才能发货
        if (order.getStatus() != STATUS_PAID) {
            throw new BusinessException("订单无法发货", ResultCode.PARAM_ERROR);
        }

        // 更新订单状态为待收货
        order.setStatus(STATUS_SHIPPED);
        
        // 如果有物流单号，可以在这里保存
        if (shipmentNumber != null && !shipmentNumber.isEmpty()) {
            // 假设这里有保存物流单号的字段
            // order.setShipmentNumber(shipmentNumber);
        }

        return updateById(order);
    }

    /**
     * 构建订单对象
     */
    private Order buildOrder(Long userId, String orderNo, OrderCreateDTO dto, Address address) {
        Order order = new Order();
        order.setOrderNo(orderNo);
        order.setUserId(userId);
        order.setPayType(dto.getPayType());
        order.setStatus(STATUS_NOT_PAY);  // 初始状态为待付款
        order.setAddressId(address.getId());
        order.setReceiverName(address.getReceiverName());
        order.setReceiverPhone(address.getReceiverPhone());
        order.setReceiverAddress(address.getProvince() + address.getCity() +
                address.getDistrict() + address.getDetailAddress());
        order.setNote(dto.getNote());

        // 初始化金额，后面会更新
        order.setTotalAmount(BigDecimal.ZERO);
        order.setPayAmount(BigDecimal.ZERO);
        order.setFreightAmount(dto.getFreightAmount() != null ? dto.getFreightAmount() : BigDecimal.ZERO);

        return order;
    }

    /**
     * 将实体转换为VO
     */
    private OrderVO convertToOrderVO(Order order) {
        OrderVO orderVO = new OrderVO();
        BeanUtils.copyProperties(order, orderVO);

        // 设置状态和支付方式描述
        orderVO.setStatusDesc(getOrderStatusDesc(order.getStatus()));
        orderVO.setPayTypeDesc(getPayTypeDesc(order.getPayType()));

        // 设置创建时间格式化字符串
        if (order.getCreateTime() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            orderVO.setCreateTimeStr(sdf.format(order.getCreateTime()));
        }

        // 设置可执行的操作
        orderVO.setAllowActions(getAllowActions(order.getStatus()));

        return orderVO;
    }

    @Override
    @Cacheable(value = "order", key = "'order:page:status:' + #status + ':orderNo:' + #orderNo + ':startTime:' + #startTime + ':endTime:' + #endTime + ':page:' + #page + ':size:' + #size")
    public Page<OrderVO> getOrderPage(long page, long size, Integer status, String orderNo, String startTime, String endTime) {
        // 构建查询条件
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();

        // 条件筛选
        if (status != null) {
            queryWrapper.eq(Order::getStatus, status);
        }

        if (orderNo != null && !orderNo.isEmpty()) {
            queryWrapper.eq(Order::getOrderNo, orderNo);
        }

        // 处理时间范围
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (startTime != null && !startTime.isEmpty()) {
            try {
                Date start = dateFormat.parse(startTime);
                queryWrapper.ge(Order::getCreateTime, start);
            } catch (ParseException e) {
                log.error("开始时间格式错误: {}", startTime);
            }
        }

        if (endTime != null && !endTime.isEmpty()) {
            try {
                Date end = dateFormat.parse(endTime);
                queryWrapper.le(Order::getCreateTime, end);
            } catch (ParseException e) {
                log.error("结束时间格式错误: {}", endTime);
            }
        }

        // 按创建时间倒序排序
        queryWrapper.orderByDesc(Order::getCreateTime);

        // 执行分页查询
        Page<Order> orderPage = page(new Page<>(page, size), queryWrapper);

        // 转换为OrderVO
        List<OrderVO> orderVOList = new ArrayList<>();
        for (Order order : orderPage.getRecords()) {
            OrderVO orderVO = convertToOrderVO(order);

            // 获取订单商品
            List<OrderItemVO> orderItems = orderItemService.getOrderItemsByOrderId(order.getId());
            orderVO.setOrderItems(orderItems);

            orderVOList.add(orderVO);
        }

        // 构建OrderVO分页结果
        Page<OrderVO> orderVOPage = new Page<>(orderPage.getCurrent(), orderPage.getSize(), orderPage.getTotal());
        orderVOPage.setRecords(orderVOList);

        return orderVOPage;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "order", key = "'orderNo:' + #orderNo")
    public boolean cancelByAdmin(String orderNo) {
        if (orderNo == null) {
            throw new BusinessException("订单编号不能为空", ResultCode.PARAM_ERROR);
        }

        // 查询订单
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Order::getOrderNo, orderNo);

        Order order = getOne(queryWrapper);
        if (order == null) {
            throw new BusinessException("订单不存在", ResultCode.NOT_FOUND);
        }

        // 只有待付款或待发货的订单才能被管理员取消
        if (order.getStatus() != STATUS_NOT_PAY && order.getStatus() != STATUS_PAID) {
            throw new BusinessException("订单状态不允许取消", ResultCode.PARAM_ERROR);
        }

        // 更新订单状态为已取消
        order.setStatus(STATUS_CANCELED);
        boolean result = updateById(order);

        if (result) {
            // 恢复商品库存和销量
            List<OrderItemVO> orderItems = orderItemService.getOrderItemsByOrderId(order.getId());
            for (OrderItemVO orderItem : orderItems) {
                // 减少销量
                productService.increaseSales(orderItem.getProductId(), -orderItem.getQuantity());

                // 增加库存
                productService.decreaseStock(orderItem.getProductId(), -orderItem.getQuantity());
            }
        }

        return result;
    }

    @Override
    @Cacheable(value = "order", key = "'order:statistics'")
    public Map<String, Object> getOrderStatistics() {
        Map<String, Object> statistics = new HashMap<>();

        try {
            // 获取订单总数
            long totalOrders = count();
            statistics.put("totalOrders", totalOrders);

            // 获取今日订单数
            long todayOrders = countTodayOrders();
            statistics.put("todayOrders", todayOrders);

            // 获取总销售额
            BigDecimal totalSales = calculateTotalSales();
            statistics.put("totalSales", totalSales);

            // 获取今日销售额
            BigDecimal todaySales = calculateTodaySales();
            statistics.put("todaySales", todaySales);

            // 获取待发货订单数
            long pendingOrders = countByStatus(STATUS_PAID);
            statistics.put("pendingOrders", pendingOrders);

            // 获取总产品数（通过productService）
            long totalProducts = productService.count();
            statistics.put("totalProducts", totalProducts);

            // 获取已售出商品数量
            long soldProducts = countSoldProducts();
            statistics.put("soldProducts", soldProducts);

            // 获取库存不足商品数（通过productService）
            long lowStockProducts = productService.countLowStockProducts(10); // 库存低于10视为库存不足
            statistics.put("lowStockProducts", lowStockProducts);

            // 获取新增评论数（通过statisticsService）
            try {
                long newReviews = statisticsService.countNewReviews(7);
                statistics.put("newReviews", newReviews);
            } catch (Exception e) {
                log.error("获取新增评论数失败", e);
                statistics.put("newReviews", 0L);
            }

            // 获取每日订单统计
            try {
                List<Map<String, Object>> dailyOrders = statisticsService.getDailyOrderStatistics(7);
                statistics.put("dailyOrders", dailyOrders);
            } catch (Exception e) {
                log.error("获取每日订单统计失败", e);
                statistics.put("dailyOrders", new ArrayList<>());
            }
        } catch (Exception e) {
            log.error("获取订单统计信息失败", e);
            // 确保至少返回一些基本数据
            statistics.put("totalOrders", 0L);
            statistics.put("todayOrders", 0L);
            statistics.put("totalSales", BigDecimal.ZERO);
            statistics.put("todaySales", BigDecimal.ZERO);
            statistics.put("pendingOrders", 0L);
            statistics.put("totalProducts", 0L);
            statistics.put("soldProducts", 0L);
            statistics.put("lowStockProducts", 0L);
            statistics.put("newReviews", 0L);
            statistics.put("dailyOrders", new ArrayList<>());
        }

        return statistics;
    }

    @Override
    @Cacheable(value = "order", key = "'order:today:count'")
    public long countTodayOrders() {
        // 获取今日开始时间
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date todayStart = calendar.getTime();

        // 查询今日订单数
        return lambdaQuery()
                .ge(Order::getCreateTime, todayStart)
                .count();
    }

    @Override
    @Cacheable(value = "order", key = "'order:total:sales'")
    public BigDecimal calculateTotalSales() {
        // 实现总销售额计算 - 使用查询代替直接调用不存在的Mapper方法
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Order::getStatus, STATUS_COMPLETED); // 只计算已完成订单

        List<Order> completedOrders = list(queryWrapper);
        return completedOrders.stream()
                .map(Order::getPayAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    @Cacheable(value = "order", key = "'order:today:sales'")
    public BigDecimal calculateTodaySales() {
        // 获取今日开始时间
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date todayStart = calendar.getTime();

        // 使用查询代替直接调用不存在的Mapper方法
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Order::getStatus, STATUS_COMPLETED) // 只计算已完成订单
                .ge(Order::getCreateTime, todayStart);

        List<Order> todayOrders = list(queryWrapper);
        return todayOrders.stream()
                .map(Order::getPayAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    @Cacheable(value = "order", key = "'order:sold:products'")
    public long countSoldProducts() {
        // 统计已售出商品数量 - 通过订单项计算
        return orderItemService.count();
    }

    @Override
    @Cacheable(value = "order", key = "'order:status:' + #status + ':count'")
    public long countByStatus(int status) {
        return lambdaQuery()
                .eq(Order::getStatus, status)
                .count();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "order", key = "'orderNo:' + #orderNo")
    public boolean updateOrderNote(String orderNo, String note) {
        if (orderNo == null) {
            throw new BusinessException("订单编号不能为空", ResultCode.PARAM_ERROR);
        }

        // 查询订单
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Order::getOrderNo, orderNo);

        Order order = getOne(queryWrapper);
        if (order == null) {
            throw new BusinessException("订单不存在", ResultCode.NOT_FOUND);
        }

        // 更新备注
        order.setNote(note);
        return updateById(order);
    }
}




