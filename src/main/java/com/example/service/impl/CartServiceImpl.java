package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.ResultCode;
import com.example.exception.BusinessException;
import com.example.mapper.CartMapper;
import com.example.model.entity.Cart;
import com.example.model.entity.Product;
import com.example.model.vo.CartItemVO;
import com.example.model.vo.CartVO;
import com.example.service.CartService;
import com.example.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author 28619
 * @description 针对表【cart(购物车表)】的数据库操作Service实现
 * @createDate 2025-04-09 17:42:18
 */
@Service
@RequiredArgsConstructor
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart>
        implements CartService {

    private final ProductService productService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "cart", key = "'user:' + #userId + ':cart'")
    public boolean add(Long userId, Long productId, Integer quantity) {
        // 参数校验
        if (userId == null || productId == null || quantity == null || quantity <= 0) {
            throw new BusinessException("参数错误", ResultCode.PARAM_ERROR);
        }

        // 检查商品是否存在且上架
        Product product = productService.getById(productId);
        if (product == null) {
            throw new BusinessException("商品不存在", ResultCode.PARAM_ERROR);
        }
        if (product.getStatus() == 0) {
            throw new BusinessException("商品已下架", ResultCode.PARAM_ERROR);
        }

        // 检查库存
        if (product.getStock() < quantity) {
            throw new BusinessException("商品库存不足", ResultCode.PARAM_ERROR);
        }

        // 检查是否已在购物车中
        LambdaQueryWrapper<Cart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Cart::getUserId, userId)
                .eq(Cart::getProductId, productId);
        Cart existCart = getOne(queryWrapper);

        boolean result;
        if (existCart != null) {
            // 已在购物车中，更新数量
            int newQuantity = existCart.getQuantity() + quantity;

            // 再次检查库存
            if (product.getStock() < newQuantity) {
                throw new BusinessException("商品库存不足", ResultCode.PARAM_ERROR);
            }

            existCart.setQuantity(newQuantity);
            result = updateById(existCart);
        } else {
            // 新增购物车项
            Cart cart = new Cart();
            cart.setUserId(userId);
            cart.setProductId(productId);
            cart.setQuantity(quantity);
            cart.setChecked(1); // 默认选中
            result = save(cart);
        }


        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "cart", key = "'user:' + #userId + ':cart'")
    public boolean remove(Long userId, Long productId) {
        // 参数校验
        if (userId == null || productId == null) {
            throw new BusinessException("参数错误", ResultCode.PARAM_ERROR);
        }

        LambdaQueryWrapper<Cart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Cart::getUserId, userId)
                .eq(Cart::getProductId, productId);

        boolean result = remove(queryWrapper);


        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "cart", key = "'user:' + #userId + ':cart'")
    public boolean removeBatch(Long userId, Long[] productIds) {
        // 参数校验
        if (userId == null || productIds == null || productIds.length == 0) {
            throw new BusinessException("参数错误", ResultCode.PARAM_ERROR);
        }

        LambdaQueryWrapper<Cart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Cart::getUserId, userId)
                .in(Cart::getProductId, Arrays.asList(productIds));

        boolean result = remove(queryWrapper);


        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "cart", key = "'user:' + #userId + ':cart'")
    public boolean clear(Long userId) {
        // 参数校验
        if (userId == null) {
            throw new BusinessException("参数错误", ResultCode.PARAM_ERROR);
        }

        // 获取用户购物车中的所有商品ID
        LambdaQueryWrapper<Cart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Cart::getUserId, userId)
                .select(Cart::getProductId);

        // 清空购物车
        queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Cart::getUserId, userId);
        boolean result = remove(queryWrapper);


        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "cart", key = "'user:' + #userId + ':cart'")
    public boolean updateQuantity(Long userId, Long productId, Integer quantity) {
        // 参数校验
        if (userId == null || productId == null || quantity == null || quantity <= 0) {
            throw new BusinessException("参数错误", ResultCode.PARAM_ERROR);
        }

        // 检查商品是否存在且上架
        Product product = productService.getById(productId);
        if (product == null) {
            throw new BusinessException("商品不存在", ResultCode.PARAM_ERROR);
        }

        // 检查库存
        if (product.getStock() < quantity) {
            throw new BusinessException("商品库存不足", ResultCode.PARAM_ERROR);
        }

        // 检查购物车项是否存在
        LambdaQueryWrapper<Cart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Cart::getUserId, userId)
                .eq(Cart::getProductId, productId);
        Cart cart = getOne(queryWrapper);

        if (cart == null) {
            throw new BusinessException("购物车中无此商品", ResultCode.PARAM_ERROR);
        }

        // 更新数量
        cart.setQuantity(quantity);
        return updateById(cart);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "cart", key = "'user:' + #userId + ':cart'")
    public boolean updateChecked(Long userId, Long productId, Integer checked) {
        // 参数校验
        if (userId == null || productId == null || checked == null || (checked != 0 && checked != 1)) {
            throw new BusinessException("参数错误", ResultCode.PARAM_ERROR);
        }

        LambdaUpdateWrapper<Cart> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Cart::getUserId, userId)
                .eq(Cart::getProductId, productId)
                .set(Cart::getChecked, checked);

        return update(updateWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "cart", key = "'user:' + #userId + ':cart'")
    public boolean updateCheckedBatch(Long userId, Long[] productIds, Integer checked) {
        // 参数校验
        if (userId == null || productIds == null || productIds.length == 0 || checked == null || (checked != 0 && checked != 1)) {
            throw new BusinessException("参数错误", ResultCode.PARAM_ERROR);
        }

        LambdaUpdateWrapper<Cart> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Cart::getUserId, userId)
                .in(Cart::getProductId, Arrays.asList(productIds))
                .set(Cart::getChecked, checked);

        return update(updateWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "cart", key = "'user:' + #userId + ':cart'")
    public boolean updateCheckedAll(Long userId, Integer checked) {
        // 参数校验
        if (userId == null || checked == null || (checked != 0 && checked != 1)) {
            throw new BusinessException("参数错误", ResultCode.PARAM_ERROR);
        }

        LambdaUpdateWrapper<Cart> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Cart::getUserId, userId)
                .set(Cart::getChecked, checked);

        return update(updateWrapper);
    }

    @Override
    @Cacheable(value = "cart", key = "'user:' + #userId + ':cart'")
    public CartVO getCartByUserId(Long userId) {
        // 参数校验
        if (userId == null) {
            throw new BusinessException("参数错误", ResultCode.PARAM_ERROR);
        }

        // 查询用户购物车项
        LambdaQueryWrapper<Cart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Cart::getUserId, userId);
        List<Cart> cartList = list(queryWrapper);

        if (cartList.isEmpty()) {
            // 购物车为空，返回空结果
            CartVO cartVO = new CartVO();
            cartVO.setCartItems(new ArrayList<>());
            cartVO.setSelectedCount(0);
            cartVO.setSelectedTotalPrice(BigDecimal.ZERO);
            cartVO.setTotalPrice(BigDecimal.ZERO);
            cartVO.setAllChecked(false);
            return cartVO;
        }

        // 获取购物车中的商品ID列表
        List<Long> productIds = cartList.stream()
                .map(Cart::getProductId)
                .collect(Collectors.toList());

        // 批量查询商品信息
        List<Product> productList = productService.listByIds(productIds);
        Map<Long, Product> productMap = productList.stream()
                .collect(Collectors.toMap(Product::getId, Function.identity()));

        // 组装购物车项视图对象
        List<CartItemVO> cartItemVOList = new ArrayList<>();
        BigDecimal totalPrice = BigDecimal.ZERO;
        BigDecimal selectedTotalPrice = BigDecimal.ZERO;
        int selectedCount = 0;
        boolean allChecked = true;

        for (Cart cart : cartList) {
            CartItemVO cartItemVO = new CartItemVO();
            cartItemVO.setId(cart.getId());
            cartItemVO.setProductId(cart.getProductId());
            cartItemVO.setUserId(cart.getUserId());
            cartItemVO.setQuantity(cart.getQuantity());
            cartItemVO.setChecked(cart.getChecked());

            // 获取商品信息
            Product product = productMap.get(cart.getProductId());
            if (product != null) {
                cartItemVO.setProductName(product.getName());
                cartItemVO.setProductImage(product.getImage());
                cartItemVO.setPrice(product.getPrice());
                cartItemVO.setStock(product.getStock());
                cartItemVO.setStatus(product.getStatus());

                // 计算总价
                BigDecimal itemTotalPrice = product.getPrice().multiply(new BigDecimal(cart.getQuantity()));
                cartItemVO.setTotalPrice(itemTotalPrice);

                // 累加总价
                totalPrice = totalPrice.add(itemTotalPrice);

                // 累加已选中的总价和数量
                if (cart.getChecked() == 1) {
                    selectedTotalPrice = selectedTotalPrice.add(itemTotalPrice);
                    selectedCount += cart.getQuantity();
                } else {
                    allChecked = false;
                }
            } else {
                // 商品已不存在，设置默认值
                cartItemVO.setProductName("商品已下架");
                cartItemVO.setProductImage("");
                cartItemVO.setPrice(BigDecimal.ZERO);
                cartItemVO.setTotalPrice(BigDecimal.ZERO);
                cartItemVO.setStock(0);
                cartItemVO.setStatus(0);
                allChecked = false;
            }

            cartItemVOList.add(cartItemVO);
        }

        // 组装购物车视图对象
        CartVO cartVO = new CartVO();
        cartVO.setCartItems(cartItemVOList);
        cartVO.setSelectedCount(selectedCount);
        cartVO.setSelectedTotalPrice(selectedTotalPrice);
        cartVO.setTotalPrice(totalPrice);
        cartVO.setAllChecked(allChecked && !cartItemVOList.isEmpty());

        return cartVO;
    }

    @Override
    @Cacheable(value = "cart", key = "'user:' + #userId + ':count'")
    public int getCartProductCount(Long userId) {
        // 参数校验
        if (userId == null) {
            throw new BusinessException("参数错误", ResultCode.PARAM_ERROR);
        }

        LambdaQueryWrapper<Cart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Cart::getUserId, userId);
        List<Cart> cartList = list(queryWrapper);

        return cartList.stream()
                .mapToInt(Cart::getQuantity)
                .sum();
    }

    @Override
    @Cacheable(value = "cart", key = "'user:' + #userId + ':product:' + #productId + ':exists'")
    public boolean existsProduct(Long userId, Long productId) {
        // 参数校验
        if (userId == null || productId == null) {
            throw new BusinessException("参数错误", ResultCode.PARAM_ERROR);
        }

        LambdaQueryWrapper<Cart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Cart::getUserId, userId)
                .eq(Cart::getProductId, productId);

        return count(queryWrapper) > 0;
    }
}




