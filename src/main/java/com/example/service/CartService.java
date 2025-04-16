package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.model.entity.Cart;
import com.example.model.vo.CartVO;

/**
 * @author 28619
 * @description 针对表【cart(购物车表)】的数据库操作Service
 * @createDate 2025-04-09 17:42:18
 */
public interface CartService extends IService<Cart> {
    /**
     * 添加商品到购物车
     *
     * @param userId    用户ID
     * @param productId 商品ID
     * @param quantity  数量
     * @return 是否成功
     */
    boolean add(Long userId, Long productId, Integer quantity);

    /**
     * 从购物车中移除商品
     *
     * @param userId    用户ID
     * @param productId 商品ID
     * @return 是否成功
     */
    boolean remove(Long userId, Long productId);

    /**
     * 批量移除商品
     *
     * @param userId     用户ID
     * @param productIds 商品ID数组
     * @return 是否成功
     */
    boolean removeBatch(Long userId, Long[] productIds);

    /**
     * 清空购物车
     *
     * @param userId 用户ID
     * @return 是否成功
     */
    boolean clear(Long userId);

    /**
     * 更新购物车商品数量
     *
     * @param userId    用户ID
     * @param productId 商品ID
     * @param quantity  数量
     * @return 是否成功
     */
    boolean updateQuantity(Long userId, Long productId, Integer quantity);

    /**
     * 更新购物车商品选中状态
     *
     * @param userId    用户ID
     * @param productId 商品ID
     * @param checked   是否选中：0-未选中，1-选中
     * @return 是否成功
     */
    boolean updateChecked(Long userId, Long productId, Integer checked);

    /**
     * 批量更新购物车商品选中状态
     *
     * @param userId     用户ID
     * @param productIds 商品ID数组
     * @param checked    是否选中：0-未选中，1-选中
     * @return 是否成功
     */
    boolean updateCheckedBatch(Long userId, Long[] productIds, Integer checked);

    /**
     * 全选/取消全选购物车商品
     *
     * @param userId  用户ID
     * @param checked 是否选中：0-未选中，1-选中
     * @return 是否成功
     */
    boolean updateCheckedAll(Long userId, Integer checked);

    /**
     * 获取用户购物车信息
     *
     * @param userId 用户ID
     * @return 购物车信息
     */
    CartVO getCartByUserId(Long userId);

    /**
     * 获取用户购物车商品数量
     *
     * @param userId 用户ID
     * @return 商品数量
     */
    int getCartProductCount(Long userId);

    /**
     * 检查商品是否在购物车中
     *
     * @param userId    用户ID
     * @param productId 商品ID
     * @return 是否存在
     */
    boolean existsProduct(Long userId, Long productId);
}
