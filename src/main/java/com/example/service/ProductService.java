package com.example.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.model.entity.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author 28619
 * @description 针对表【product(商品表)】的数据库操作Service
 * @createDate 2025-04-09 17:42:33
 */
public interface ProductService extends IService<Product> {
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
    Page<Product> getProductPage(long page, long size, Long categoryId, String keyword,
                                 Double minPrice, Double maxPrice);

    /**
     * 获取热门商品列表
     *
     * @param limit 数量限制
     * @return 热门商品列表
     */
    List<Product> getHotProducts(int limit);

    /**
     * 获取最新商品列表
     *
     * @param limit 数量限制
     * @return 最新商品列表
     */
    List<Product> getNewProducts(int limit);

    /**
     * 根据分类ID获取商品列表
     *
     * @param categoryId 分类ID
     * @param limit      数量限制
     * @return 商品列表
     */
    List<Product> getProductsByCategory(Long categoryId, int limit);

    /**
     * 增加商品销量
     *
     * @param productId 商品ID
     * @param quantity  增加数量
     * @return 是否成功
     */
    boolean increaseSales(Long productId, int quantity);

    /**
     * 减少商品库存
     *
     * @param productId 商品ID
     * @param quantity  减少数量
     * @return 是否成功
     */
    boolean decreaseStock(Long productId, int quantity);

    /**
     * 批量更新商品状态
     *
     * @param productIds 商品ID列表
     * @param status     状态：0-下架，1-上架
     * @return 是否全部成功
     */
    boolean batchUpdateProductStatus(List<Long> productIds, Integer status);

    /**
     * 上传商品主图
     *
     * @param file      图片文件
     * @param productId 商品ID
     * @return 图片URL
     */
    String uploadImage(MultipartFile file, Long productId);

    /**
     * 上传商品图片（添加到图片集）
     *
     * @param file      图片文件
     * @param productId 商品ID
     * @return 图片URL
     */
    String uploadProductImage(MultipartFile file, Long productId);

    /**
     * 删除商品图片（从图片集中删除）
     *
     * @param productId 商品ID
     * @param imageUrl  图片URL
     * @return 是否成功
     */
    boolean deleteProductImage(Long productId, String imageUrl);

    /**
     * 获取商品详情并记录用户浏览行为
     *
     * @param id     商品ID
     * @param userId 用户ID，可为null（未登录）
     * @return 商品详情
     */
    Product getProductDetailAndRecordView(Long id, Long userId);

    /**
     * 统计库存低于指定阈值的商品数量
     *
     * @param threshold 库存阈值
     * @return 低库存商品数量
     */
    long countLowStockProducts(int threshold);

    /**
     * 统计上架中（活跃）的商品数量
     *
     * @return 上架商品数量
     */
    long countActiveProducts();
}
