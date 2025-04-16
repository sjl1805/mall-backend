package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.model.entity.OrderItem;
import com.example.model.vo.CartItemVO;
import com.example.model.vo.OrderItemVO;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 28619
 * @description 针对表【order_item(订单商品表)】的数据库操作Service
 * @createDate 2025-04-09 17:42:30
 */
public interface OrderItemService extends IService<OrderItem> {
    /**
     * 批量保存订单商品
     *
     * @param orderNo   订单编号
     * @param orderId   订单ID
     * @param cartItems 购物车项列表
     * @return 订单商品总金额
     */
    BigDecimal saveOrderItems(String orderNo, Long orderId, List<CartItemVO> cartItems);

    /**
     * 保存单个商品的订单商品
     *
     * @param orderNo   订单编号
     * @param orderId   订单ID
     * @param productId 商品ID
     * @param quantity  数量
     * @return 订单商品总金额
     */
    BigDecimal saveOrderItem(String orderNo, Long orderId, Long productId, Integer quantity);

    /**
     * 根据订单ID获取订单商品列表
     *
     * @param orderId 订单ID
     * @return 订单商品列表
     */
    List<OrderItemVO> getOrderItemsByOrderId(Long orderId);

    /**
     * 根据订单编号获取订单商品列表
     *
     * @param orderNo 订单编号
     * @return 订单商品列表
     */
    List<OrderItemVO> getOrderItemsByOrderNo(String orderNo);

    /**
     * 删除订单商品（逻辑删除）
     *
     * @param orderId 订单ID
     * @return 是否成功
     */
    boolean deleteByOrderId(Long orderId);

    /**
     * 删除订单商品（逻辑删除）
     *
     * @param orderNo 订单编号
     * @return 是否成功
     */
    boolean deleteByOrderNo(String orderNo);
}
