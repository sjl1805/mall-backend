package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.ResultCode;
import com.example.exception.BusinessException;
import com.example.mapper.OrderItemMapper;
import com.example.model.entity.OrderItem;
import com.example.model.entity.Product;
import com.example.model.vo.CartItemVO;
import com.example.model.vo.OrderItemVO;
import com.example.service.OrderItemService;
import com.example.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 28619
 * @description 针对表【order_item(订单商品表)】的数据库操作Service实现
 * @createDate 2025-04-09 17:42:30
 */
@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem>
        implements OrderItemService {

    private final ProductService productService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BigDecimal saveOrderItems(String orderNo, Long orderId, List<CartItemVO> cartItems) {
        if (orderNo == null || orderId == null || cartItems == null || cartItems.isEmpty()) {
            throw new BusinessException("参数错误", ResultCode.PARAM_ERROR);
        }

        BigDecimal totalAmount = BigDecimal.ZERO;
        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItemVO cartItem : cartItems) {
            // 检查商品是否有效
            if (cartItem.getStatus() == 0) {
                throw new BusinessException("商品" + cartItem.getProductName() + "已下架", ResultCode.PARAM_ERROR);
            }

            // 计算商品价格
            BigDecimal itemTotalPrice = cartItem.getPrice().multiply(new BigDecimal(cartItem.getQuantity()));
            totalAmount = totalAmount.add(itemTotalPrice);

            // 创建订单商品
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(orderId);
            orderItem.setOrderNo(orderNo);
            orderItem.setProductId(cartItem.getProductId());
            orderItem.setProductName(cartItem.getProductName());
            orderItem.setProductImage(cartItem.getProductImage());
            orderItem.setPrice(cartItem.getPrice());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setTotalPrice(itemTotalPrice);

            orderItems.add(orderItem);

            // 更新商品库存和销量
            productService.decreaseStock(cartItem.getProductId(), cartItem.getQuantity());
            productService.increaseSales(cartItem.getProductId(), cartItem.getQuantity());
        }

        // 批量保存订单商品
        saveBatch(orderItems);

        return totalAmount;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BigDecimal saveOrderItem(String orderNo, Long orderId, Long productId, Integer quantity) {
        if (orderNo == null || orderId == null || productId == null || quantity == null || quantity <= 0) {
            throw new BusinessException("参数错误", ResultCode.PARAM_ERROR);
        }

        // 获取商品信息
        Product product = productService.getById(productId);
        if (product == null) {
            throw new BusinessException("商品不存在", ResultCode.PARAM_ERROR);
        }

        if (product.getStatus() == 0) {
            throw new BusinessException("商品已下架", ResultCode.PARAM_ERROR);
        }

        if (product.getStock() < quantity) {
            throw new BusinessException("商品库存不足", ResultCode.PARAM_ERROR);
        }

        // 计算商品价格
        BigDecimal totalPrice = product.getPrice().multiply(new BigDecimal(quantity));

        // 创建订单商品
        OrderItem orderItem = new OrderItem();
        orderItem.setOrderId(orderId);
        orderItem.setOrderNo(orderNo);
        orderItem.setProductId(productId);
        orderItem.setProductName(product.getName());
        orderItem.setProductImage(product.getImage());
        orderItem.setPrice(product.getPrice());
        orderItem.setQuantity(quantity);
        orderItem.setTotalPrice(totalPrice);

        // 保存订单商品
        save(orderItem);

        // 更新商品库存和销量
        productService.decreaseStock(productId, quantity);
        productService.increaseSales(productId, quantity);

        return totalPrice;
    }

    @Override
    public List<OrderItemVO> getOrderItemsByOrderId(Long orderId) {
        if (orderId == null) {
            return new ArrayList<>();
        }

        LambdaQueryWrapper<OrderItem> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OrderItem::getOrderId, orderId);

        List<OrderItem> orderItems = list(queryWrapper);

        return convertToOrderItemVOList(orderItems);
    }

    @Override
    public List<OrderItemVO> getOrderItemsByOrderNo(String orderNo) {
        if (orderNo == null) {
            return new ArrayList<>();
        }

        LambdaQueryWrapper<OrderItem> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OrderItem::getOrderNo, orderNo);

        List<OrderItem> orderItems = list(queryWrapper);

        return convertToOrderItemVOList(orderItems);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteByOrderId(Long orderId) {
        if (orderId == null) {
            return false;
        }

        LambdaUpdateWrapper<OrderItem> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(OrderItem::getOrderId, orderId);

        return remove(updateWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteByOrderNo(String orderNo) {
        if (orderNo == null) {
            return false;
        }

        LambdaUpdateWrapper<OrderItem> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(OrderItem::getOrderNo, orderNo);

        return remove(updateWrapper);
    }

    /**
     * 将实体列表转换为VO列表
     */
    private List<OrderItemVO> convertToOrderItemVOList(List<OrderItem> orderItems) {
        return orderItems.stream().map(orderItem -> {
            OrderItemVO vo = new OrderItemVO();
            BeanUtils.copyProperties(orderItem, vo);
            return vo;
        }).collect(Collectors.toList());
    }
}




