package com.example.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.PageResult;
import com.example.common.Result;
import com.example.model.entity.Category;
import com.example.model.entity.Product;
import com.example.service.CategoryService;
import com.example.service.ProductService;
import com.example.service.ProductSimilarityService;
import com.example.util.UserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品控制器
 * 处理商品和分类相关的前台请求
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductSimilarityService productSimilarityService;
    private final CategoryService categoryService;
    private final UserUtil userUtil;

    // ==================== 商品相关接口 ====================

    /**
     * 分页查询商品列表
     *
     * @param page       页码
     * @param size       每页数量
     * @param categoryId 分类ID（可选）
     * @param keyword    关键词（可选）
     * @param minPrice   最小价格（可选）
     * @param maxPrice   最大价格（可选）
     * @return 商品分页结果
     */
    @GetMapping("/product/page")
    public Result<PageResult<Product>> getProductPage(
            @RequestParam(defaultValue = "1") long page,
            @RequestParam(defaultValue = "10") long size,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice) {

        Page<Product> productPage = productService.getProductPage(page, size, categoryId, keyword, minPrice, maxPrice);

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
     * @param id             商品ID
     * @param includeSimilar 是否包含相似商品，默认true
     * @param similarLimit   相似商品数量限制，默认5
     * @return 商品详情及相似商品
     */
    @GetMapping("/product/{id}")
    public Result<Map<String, Object>> getProductDetail(
            @PathVariable Long id,
            @RequestParam(defaultValue = "true") boolean includeSimilar,
            @RequestParam(defaultValue = "5") int similarLimit) {
        // 获取当前用户ID（未登录则为null）
        Long userId = userUtil.getCurrentUserIdOrNull();

        // 获取商品详情并记录浏览行为
        Product product = productService.getProductDetailAndRecordView(id, userId);

        if (product == null || product.getStatus() == 0) {
            return Result.error("商品不存在或已下架", 404);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("product", product);

        // 如果需要，获取相似商品
        if (includeSimilar) {
            List<Product> similarProducts = productSimilarityService.getMostSimilarProducts(id, similarLimit);
            result.put("similarProducts", similarProducts);
        }

        return Result.success(result);
    }

    /**
     * 获取热门商品
     *
     * @param limit 数量限制
     * @return 热门商品列表
     */
    @GetMapping("/product/hot")
    public Result<List<Product>> getHotProducts(@RequestParam(defaultValue = "10") int limit) {
        List<Product> products = productService.getHotProducts(limit);
        return Result.success(products);
    }

    /**
     * 获取最新商品
     *
     * @param limit 数量限制
     * @return 最新商品列表
     */
    @GetMapping("/product/new")
    public Result<List<Product>> getNewProducts(@RequestParam(defaultValue = "10") int limit) {
        List<Product> products = productService.getNewProducts(limit);
        return Result.success(products);
    }

    /**
     * 获取分类商品
     *
     * @param categoryId 分类ID
     * @param limit      数量限制
     * @return 分类商品列表
     */
    @GetMapping("/product/category/{categoryId}")
    public Result<List<Product>> getProductsByCategory(
            @PathVariable Long categoryId,
            @RequestParam(defaultValue = "10") int limit) {
        List<Product> products = productService.getProductsByCategory(categoryId, limit);
        return Result.success(products);
    }

    /**
     * 获取相似商品
     *
     * @param productId 商品ID
     * @param limit     数量限制
     * @return 相似商品列表
     */
    @GetMapping("/product/{productId}/similar")
    public Result<List<Product>> getSimilarProducts(
            @PathVariable Long productId,
            @RequestParam(defaultValue = "5") int limit) {
        if (productId == null) {
            return Result.error("商品ID不能为空", 400);
        }
        List<Product> products = productSimilarityService.getMostSimilarProducts(productId, limit);
        return Result.success(products);
    }

    // ==================== 分类相关接口 ====================

    /**
     * 获取启用的分类列表（平铺）
     *
     * @return 分类列表
     */
    @GetMapping("/category/list")
    public Result<List<Category>> getEnabledCategories() {
        List<Category> categories = categoryService.listEnabledCategories();
        return Result.success(categories);
    }

    /**
     * 获取启用的分类树结构
     *
     * @return 分类树
     */
    @GetMapping("/category/tree")
    public Result<List<Category>> getCategoryTree() {
        // 获取分类树并过滤掉禁用的分类
        List<Category> categoryTree = categoryService.getCategoryTree();
        return Result.success(categoryTree);
    }

    /**
     * 根据ID获取分类详情
     *
     * @param categoryId 分类ID
     * @return 分类详情
     */
    @GetMapping("/category/{categoryId}")
    public Result<Category> getCategoryDetail(@PathVariable Long categoryId) {
        Category category = categoryService.getById(categoryId);

        if (category == null || category.getStatus() == 0) {
            return Result.error("分类不存在或已禁用");
        }

        return Result.success(category);
    }

    /**
     * 根据父ID获取子分类
     *
     * @param parentId 父分类ID
     * @return 子分类列表
     */
    @GetMapping("/category/children/{parentId}")
    public Result<List<Category>> getChildrenCategories(@PathVariable Long parentId) {
        List<Category> children = categoryService.getChildrenCategories(parentId);
        return Result.success(children);
    }
} 