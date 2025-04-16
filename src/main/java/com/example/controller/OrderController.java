package com.example.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.Result;
import com.example.model.dto.OrderCreateDTO;
import com.example.model.vo.OrderVO;
import com.example.service.OrderService;
import com.example.util.UserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

/**
 * 订单控制器
 */
@Slf4j
@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final UserUtil userUtil;

    /**
     * 创建订单
     *
     * @param orderCreateDTO 创建订单参数
     * @return 订单号
     */
    @PostMapping("/create")
    public Result<String> createOrder(@RequestBody @Valid OrderCreateDTO orderCreateDTO) {
        Long userId = userUtil.getCurrentUserId();
        String orderNo = orderService.create(userId, orderCreateDTO);
        return Result.success(orderNo, "订单创建成功");
    }

    /**
     * 获取订单详情
     *
     * @param orderNo 订单号
     * @return 订单详情
     */
    @GetMapping("/detail")
    public Result<OrderVO> getOrderDetail(@RequestParam String orderNo) {
        Long userId = userUtil.getCurrentUserId();
        OrderVO orderVO = orderService.getOrderDetail(userId, orderNo);
        return Result.success(orderVO);
    }

    /**
     * 取消订单
     *
     * @param orderNo 订单号
     * @return 取消结果
     */
    @PostMapping("/cancel")
    public Result<Boolean> cancelOrder(@RequestParam String orderNo) {
        Long userId = userUtil.getCurrentUserId();
        boolean result = orderService.cancel(userId, orderNo);
        return Result.success(result, "订单取消成功");
    }

    /**
     * 支付订单
     *
     * @param orderNo 订单号
     * @param payType 支付方式：1-支付宝，2-微信
     * @return 支付信息
     */
    @PostMapping("/pay")
    public Result<Map<String, String>> payOrder(
            @RequestParam String orderNo,
            @RequestParam Integer payType) {
        Long userId = userUtil.getCurrentUserId();
        Map<String, String> payInfo = orderService.pay(userId, orderNo, payType);
        return Result.success(payInfo, "获取支付参数成功");
    }

    /**
     * 确认收货
     *
     * @param orderNo 订单号
     * @return 确认结果
     */
    @PostMapping("/confirm")
    public Result<Boolean> confirmReceipt(@RequestParam String orderNo) {
        Long userId = userUtil.getCurrentUserId();
        boolean result = orderService.confirm(userId, orderNo);
        return Result.success(result, "确认收货成功");
    }

    /**
     * 删除订单
     *
     * @param orderNo 订单号
     * @return 删除结果
     */
    @DeleteMapping("/delete")
    public Result<Boolean> deleteOrder(@RequestParam String orderNo) {
        Long userId = userUtil.getCurrentUserId();
        boolean result = orderService.deleteOrder(userId, orderNo);
        return Result.success(result, "订单删除成功");
    }

    /**
     * 获取用户订单列表
     *
     * @param status 订单状态：0-待付款，1-待发货，2-待收货，3-已完成，4-已取消，null-全部
     * @param page   页码
     * @param size   每页数量
     * @return 订单列表
     */
    @GetMapping("/list")
    public Result<Page<OrderVO>> getUserOrders(
            @RequestParam(required = false) Integer status,
            @RequestParam(defaultValue = "1") long page,
            @RequestParam(defaultValue = "10") long size) {
        Long userId = userUtil.getCurrentUserId();
        Page<OrderVO> orderPage = orderService.getUserOrders(userId, status, page, size);
        return Result.success(orderPage);
    }

    /**
     * 订单支付回调（模拟）
     * 注意：实际项目中，这个接口通常由支付平台回调，需要进行签名验证等安全措施
     *
     * @param orderNo 订单号
     * @param tradeNo 交易号
     * @return 处理结果
     */
    @PostMapping("/pay/callback")
    public Result<Boolean> payCallback(
            @RequestParam String orderNo,
            @RequestParam String tradeNo) {
        boolean result = orderService.payCallback(orderNo, tradeNo);
        return Result.success(result, "支付回调处理成功");
    }

} 