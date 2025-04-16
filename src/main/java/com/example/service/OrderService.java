package com.example.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.model.dto.OrderCreateDTO;
import com.example.model.entity.Order;
import com.example.model.vo.OrderVO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author 28619
 * @description 针对表【order(订单表)】的数据库操作Service
 * @createDate 2025-04-09 17:42:27
 */
public interface OrderService extends IService<Order> {
    /**
     * 创建订单
     *
     * @param userId         用户ID
     * @param orderCreateDTO 创建订单参数
     * @return 订单编号
     */
    String create(Long userId, OrderCreateDTO orderCreateDTO);

    /**
     * 获取订单详情
     *
     * @param userId  用户ID
     * @param orderNo 订单编号
     * @return 订单详情
     */
    OrderVO getOrderDetail(Long userId, String orderNo);

    /**
     * 取消订单
     *
     * @param userId  用户ID
     * @param orderNo 订单编号
     * @return 是否成功
     */
    boolean cancel(Long userId, String orderNo);

    /**
     * 支付订单
     *
     * @param userId  用户ID
     * @param orderNo 订单编号
     * @param payType 支付方式
     * @return 支付结果
     */
    Map<String, String> pay(Long userId, String orderNo, Integer payType);

    /**
     * 订单支付回调
     *
     * @param orderNo 订单编号
     * @param tradeNo 交易号
     * @return 是否成功
     */
    boolean payCallback(String orderNo, String tradeNo);

    /**
     * 确认收货
     *
     * @param userId  用户ID
     * @param orderNo 订单编号
     * @return 是否成功
     */
    boolean confirm(Long userId, String orderNo);

    /**
     * 获取用户订单列表
     *
     * @param userId 用户ID
     * @param status 订单状态
     * @param page   页码
     * @param size   每页大小
     * @return 订单分页
     */
    Page<OrderVO> getUserOrders(Long userId, Integer status, long page, long size);

    /**
     * 获取订单状态文字描述
     *
     * @param status 状态码
     * @return 文字描述
     */
    String getOrderStatusDesc(Integer status);

    /**
     * 获取支付方式文字描述
     *
     * @param payType 支付方式
     * @return 文字描述
     */
    String getPayTypeDesc(Integer payType);

    /**
     * 获取订单允许的操作
     *
     * @param orderStatus 订单状态
     * @return 操作列表
     */
    List<String> getAllowActions(Integer orderStatus);

    /**
     * 删除订单（逻辑删除）
     *
     * @param userId  用户ID
     * @param orderNo 订单编号
     * @return 是否成功
     */
    boolean deleteOrder(Long userId, String orderNo);

    /**
     * 管理员发货
     *
     * @param orderNo        订单编号
     * @param shipmentNumber 物流单号
     * @return 是否成功
     */
    boolean ship(String orderNo, String shipmentNumber);

    /**
     * 管理员获取订单列表（分页）
     *
     * @param page      页码
     * @param size      每页数量
     * @param status    订单状态（可选）
     * @param orderNo   订单编号（可选）
     * @param startTime 开始时间（可选）
     * @param endTime   结束时间（可选）
     * @return 订单分页
     */
    Page<OrderVO> getOrderPage(long page, long size, Integer status, String orderNo, String startTime, String endTime);

    /**
     * 管理员取消订单
     *
     * @param orderNo 订单编号
     * @return 是否成功
     */
    boolean cancelByAdmin(String orderNo);

    /**
     * 获取订单统计信息
     *
     * @return 订单统计信息，包括总订单数、今日订单数、本周订单数、本月订单数、总销售额等
     */
    Map<String, Object> getOrderStatistics();

    /**
     * 统计指定状态的订单数量
     *
     * @param status 订单状态
     * @return 订单数量
     */
    long countByStatus(int status);

    /**
     * 更新订单备注
     *
     * @param orderNo 订单编号
     * @param note    备注内容
     * @return 是否成功
     */
    boolean updateOrderNote(String orderNo, String note);

    /**
     * 统计今日订单数量
     *
     * @return 今日订单数量
     */
    long countTodayOrders();

    /**
     * 计算总销售额
     *
     * @return 总销售额
     */
    BigDecimal calculateTotalSales();

    /**
     * 计算今日销售额
     *
     * @return 今日销售额
     */
    BigDecimal calculateTodaySales();

    /**
     * 统计已售出商品数量
     *
     * @return 已售商品数量
     */
    long countSoldProducts();
}
