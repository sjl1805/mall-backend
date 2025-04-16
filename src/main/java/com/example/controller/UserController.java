package com.example.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.Result;
import com.example.model.entity.Address;
import com.example.model.entity.Product;
import com.example.model.entity.User;
import com.example.model.vo.CartVO;
import com.example.service.AddressService;
import com.example.service.CartService;
import com.example.service.FavoriteService;
import com.example.service.UserService;
import com.example.util.FileUtil;
import com.example.util.UserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户控制器
 * 处理与用户相关的操作，包括用户信息管理、地址管理、购物车管理和收藏管理
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AddressService addressService;
    private final CartService cartService;
    private final FavoriteService favoriteService;
    private final UserUtil userUtil;
    private final FileUtil fileUtil;

    // ==================== 用户基本信息相关接口 ====================

    /**
     * 获取当前登录用户信息
     *
     * @return 用户信息
     */
    @GetMapping("/user/info")
    public Result<User> getCurrentUser() {
        Long userId = userUtil.getCurrentUserId();
        User user = userService.getById(userId);

        // 出于安全考虑，不返回密码
        if (user != null) {
            user.setPassword(null);
        }

        return Result.success(user);
    }

    /**
     * 更新用户个人信息
     *
     * @param user 用户信息
     * @return 更新结果
     */
    @PutMapping("/user/info")
    public Result<Boolean> updateUserInfo(@RequestBody User user) {
        // 确保只能修改自己的信息
        Long currentUserId = userUtil.getCurrentUserId();
        user.setId(currentUserId);

        // 设置不允许用户自行修改的字段为null
        user.setRole(null);
        user.setStatus(null);

        boolean result = userService.updateUser(user);
        return Result.success(result, "更新成功");
    }

    /**
     * 修改密码
     *
     * @param params 包含旧密码和新密码的Map
     * @return 修改结果
     */
    @PutMapping("/user/password")
    public Result<Boolean> updatePassword(@RequestBody Map<String, String> params) {
        String oldPassword = params.get("oldPassword");
        String newPassword = params.get("newPassword");

        if (oldPassword == null || newPassword == null) {
            return Result.error("参数错误");
        }

        Long currentUserId = userUtil.getCurrentUserId();
        boolean result = userService.updatePassword(currentUserId, oldPassword, newPassword);

        return Result.success(result, "密码修改成功");
    }

    /**
     * 上传头像
     *
     * @param file 头像文件
     * @return 头像URL
     */
    @PostMapping("/user/avatar")
    public Result<String> uploadAvatar(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return Result.error("请选择头像文件", 400);
        }

        // 上传文件
        String fileUrl = fileUtil.uploadFile(file);

        // 更新用户头像
        Long userId = userUtil.getCurrentUserId();
        User user = new User();
        user.setId(userId);
        user.setAvatar(fileUrl);
        userService.updateById(user);

        return Result.success(fileUrl, "头像上传成功");
    }

    /**
     * 检查用户名是否存在
     *
     * @param username 用户名
     * @return 检查结果
     */
    @GetMapping("/user/check-username")
    public Result<Map<String, Boolean>> checkUsername(@RequestParam String username) {
        boolean exists = userService.existsByUsername(username);
        Map<String, Boolean> result = new HashMap<>();
        result.put("exists", exists);
        return Result.success(result);
    }

    // ==================== 地址管理相关接口 ====================

    /**
     * 获取用户地址列表
     *
     * @return 地址列表
     */
    @GetMapping("/user/address/list")
    public Result<List<Address>> getUserAddresses() {
        Long userId = userUtil.getCurrentUserId();
        List<Address> addresses = addressService.getUserAddresses(userId);
        return Result.success(addresses);
    }

    /**
     * 获取默认地址
     *
     * @return 默认地址
     */
    @GetMapping("/user/address/default")
    public Result<Address> getDefaultAddress() {
        Long userId = userUtil.getCurrentUserId();
        Address address = addressService.getDefaultAddress(userId);
        return Result.success(address);
    }

    /**
     * 添加地址
     *
     * @param address 地址信息
     * @return 添加后的地址
     */
    @PostMapping("/user/address")
    public Result<Address> addAddress(@RequestBody @Valid Address address) {
        Long userId = userUtil.getCurrentUserId();
        Address savedAddress = addressService.addAddress(address, userId);
        return Result.success(savedAddress, "地址添加成功");
    }

    /**
     * 更新地址
     *
     * @param address 地址信息
     * @return 更新结果
     */
    @PutMapping("/user/address")
    public Result<Boolean> updateAddress(@RequestBody @Valid Address address) {
        Long userId = userUtil.getCurrentUserId();
        boolean result = addressService.updateAddress(address, userId);
        return Result.success(result, "地址更新成功");
    }

    /**
     * 删除地址
     *
     * @param addressId 地址ID
     * @return 删除结果
     */
    @DeleteMapping("/user/address/{addressId}")
    public Result<Boolean> deleteAddress(@PathVariable Long addressId) {
        Long userId = userUtil.getCurrentUserId();
        boolean result = addressService.deleteAddress(addressId, userId);
        return Result.success(result, "地址删除成功");
    }

    /**
     * 设置默认地址
     *
     * @param addressId 地址ID
     * @return 设置结果
     */
    @PutMapping("/user/address/default/{addressId}")
    public Result<Boolean> setDefaultAddress(@PathVariable Long addressId) {
        Long userId = userUtil.getCurrentUserId();
        boolean result = addressService.setDefaultAddress(addressId, userId);
        return Result.success(result, "默认地址设置成功");
    }

    /**
     * 获取地址详情
     *
     * @param addressId 地址ID
     * @return 地址详情
     */
    @GetMapping("/user/address/{addressId}")
    public Result<Address> getAddressDetail(@PathVariable Long addressId) {
        Long userId = userUtil.getCurrentUserId();

        // 获取地址
        Address address = addressService.getById(addressId);
        if (address == null) {
            return Result.error("地址不存在", 404);
        }

        // 验证地址是否属于该用户
        if (!address.getUserId().equals(userId)) {
            return Result.error("无权查看该地址", 403);
        }

        return Result.success(address);
    }

    // ==================== 购物车相关接口 ====================

    /**
     * 获取购物车信息
     *
     * @return 购物车信息
     */
    @GetMapping("/user/cart")
    public Result<CartVO> getCart() {
        Long userId = userUtil.getCurrentUserId();
        CartVO cartVO = cartService.getCartByUserId(userId);
        return Result.success(cartVO);
    }

    /**
     * 获取购物车商品数量
     *
     * @return 商品数量
     */
    @GetMapping("/user/cart/count")
    public Result<Map<String, Integer>> getCartCount() {
        Long userId = userUtil.getCurrentUserId();
        int count = cartService.getCartProductCount(userId);
        Map<String, Integer> result = new HashMap<>();
        result.put("count", count);
        return Result.success(result);
    }

    /**
     * 添加商品到购物车
     *
     * @param productId 商品ID
     * @param quantity  数量
     * @return 添加结果
     */
    @PostMapping("/user/cart/add")
    public Result<Boolean> addToCart(@RequestParam Long productId, @RequestParam(defaultValue = "1") Integer quantity) {
        Long userId = userUtil.getCurrentUserId();
        boolean result = cartService.add(userId, productId, quantity);
        return Result.success(result, "添加成功");
    }

    /**
     * 更新购物车商品数量
     *
     * @param productId 商品ID
     * @param quantity  数量
     * @return 更新结果
     */
    @PutMapping("/user/cart/update")
    public Result<Boolean> updateCartQuantity(@RequestParam Long productId, @RequestParam Integer quantity) {
        Long userId = userUtil.getCurrentUserId();
        boolean result = cartService.updateQuantity(userId, productId, quantity);
        return Result.success(result, "更新成功");
    }

    /**
     * 删除购物车商品
     *
     * @param productId 商品ID
     * @return 删除结果
     */
    @DeleteMapping("/user/cart/delete")
    public Result<Boolean> deleteCartItem(@RequestParam Long productId) {
        Long userId = userUtil.getCurrentUserId();
        boolean result = cartService.remove(userId, productId);
        return Result.success(result, "删除成功");
    }

    /**
     * 批量删除购物车商品
     *
     * @param productIds 商品ID数组
     * @return 删除结果
     */
    @DeleteMapping("/user/cart/delete/batch")
    public Result<Boolean> deleteCartItemBatch(@RequestParam Long[] productIds) {
        Long userId = userUtil.getCurrentUserId();
        boolean result = cartService.removeBatch(userId, productIds);
        return Result.success(result, "批量删除成功");
    }

    /**
     * 清空购物车
     *
     * @return 清空结果
     */
    @DeleteMapping("/user/cart/clear")
    public Result<Boolean> clearCart() {
        Long userId = userUtil.getCurrentUserId();
        boolean result = cartService.clear(userId);
        return Result.success(result, "清空购物车成功");
    }

    /**
     * 选中/取消选中购物车商品
     *
     * @param productId 商品ID
     * @param checked   是否选中：0-未选中，1-选中
     * @return 更新结果
     */
    @PutMapping("/user/cart/checked")
    public Result<Boolean> updateCartChecked(@RequestParam Long productId, @RequestParam Integer checked) {
        Long userId = userUtil.getCurrentUserId();
        boolean result = cartService.updateChecked(userId, productId, checked);
        return Result.success(result, "更新选中状态成功");
    }

    /**
     * 批量选中/取消选中购物车商品
     *
     * @param productIds 商品ID数组
     * @param checked    是否选中：0-未选中，1-选中
     * @return 更新结果
     */
    @PutMapping("/user/cart/checked/batch")
    public Result<Boolean> updateCartCheckedBatch(@RequestParam Long[] productIds, @RequestParam Integer checked) {
        Long userId = userUtil.getCurrentUserId();
        boolean result = cartService.updateCheckedBatch(userId, productIds, checked);
        return Result.success(result, "批量更新选中状态成功");
    }

    /**
     * 全选/取消全选购物车商品
     *
     * @param checked 是否选中：0-未选中，1-选中
     * @return 更新结果
     */
    @PutMapping("/user/cart/checked/all")
    public Result<Boolean> updateCartCheckedAll(@RequestParam Integer checked) {
        Long userId = userUtil.getCurrentUserId();
        boolean result = cartService.updateCheckedAll(userId, checked);
        return Result.success(result, "全选/取消全选成功");
    }

    /**
     * 检查商品是否在购物车中
     *
     * @param productId 商品ID
     * @return 检查结果
     */
    @GetMapping("/user/cart/exists")
    public Result<Map<String, Boolean>> existsProductInCart(@RequestParam Long productId) {
        Long userId = userUtil.getCurrentUserId();
        boolean exists = cartService.existsProduct(userId, productId);
        Map<String, Boolean> result = new HashMap<>();
        result.put("exists", exists);
        return Result.success(result);
    }

    // ==================== 收藏相关接口 ====================

    /**
     * 添加收藏
     *
     * @param productId 商品ID
     * @return 收藏结果
     */
    @PostMapping("/user/favorite/add")
    public Result<Boolean> addFavorite(@RequestParam Long productId) {
        Long userId = userUtil.getCurrentUserId();
        boolean result = favoriteService.addFavorite(userId, productId);
        return Result.success(result, "收藏成功");
    }

    /**
     * 取消收藏
     *
     * @param productId 商品ID
     * @return 取消结果
     */
    @DeleteMapping("/user/favorite/remove")
    public Result<Boolean> removeFavorite(@RequestParam Long productId) {
        Long userId = userUtil.getCurrentUserId();
        boolean result = favoriteService.removeFavorite(userId, productId);
        return Result.success(result, "取消收藏成功");
    }

    /**
     * 检查商品是否已收藏
     *
     * @param productId 商品ID
     * @return 是否已收藏
     */
    @GetMapping("/user/favorite/check")
    public Result<Map<String, Boolean>> checkFavorite(@RequestParam Long productId) {
        Long userId = userUtil.getCurrentUserId();
        boolean isFavorite = favoriteService.isFavorite(userId, productId);

        Map<String, Boolean> result = new HashMap<>();
        result.put("favorite", isFavorite);

        return Result.success(result);
    }

    /**
     * 获取用户收藏的商品列表
     *
     * @param page 页码
     * @param size 每页数量
     * @return 收藏商品分页
     */
    @GetMapping("/user/favorite/list")
    public Result<Page<Product>> getUserFavorites(
            @RequestParam(defaultValue = "1") long page,
            @RequestParam(defaultValue = "10") long size) {
        Long userId = userUtil.getCurrentUserId();
        Page<Product> favoriteProducts = favoriteService.getUserFavorites(userId, page, size);
        return Result.success(favoriteProducts);
    }

    /**
     * 获取用户收藏数量
     *
     * @return 收藏数量
     */
    @GetMapping("/user/favorite/count")
    public Result<Map<String, Integer>> getFavoriteCount() {
        Long userId = userUtil.getCurrentUserId();
        int count = favoriteService.getUserFavoriteCount(userId);

        Map<String, Integer> result = new HashMap<>();
        result.put("count", count);

        return Result.success(result);
    }

    /**
     * 获取用户最近收藏的商品
     *
     * @param limit 数量限制
     * @return 商品列表
     */
    @GetMapping("/user/favorite/recent")
    public Result<List<Product>> getRecentFavorites(
            @RequestParam(defaultValue = "5") int limit) {
        Long userId = userUtil.getCurrentUserId();
        List<Product> recentFavorites = favoriteService.getRecentFavorites(userId, limit);
        return Result.success(recentFavorites);
    }
} 