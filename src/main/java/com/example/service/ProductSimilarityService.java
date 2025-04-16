package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.model.entity.Product;
import com.example.model.entity.ProductSimilarity;

import java.util.List;

/**
 * @author 28619
 * @description 针对表【product_similarity(商品相似度表)】的数据库操作Service
 * @createDate 2025-04-09 17:42:36
 */
public interface ProductSimilarityService extends IService<ProductSimilarity> {

    /**
     * 计算并更新所有商品间的相似度
     * 这是一个批量操作，会计算所有商品之间的相似度并更新到数据库
     * 适合定时任务执行，如每天凌晨执行一次
     *
     * @return 计算更新的商品对数量
     */
    int calculateAndUpdateAllProductSimilarities();

    /**
     * 计算并更新指定商品集合间的相似度
     *
     * @param productIds 需要计算相似度的商品ID集合
     * @return 计算更新的商品对数量
     */
    int calculateAndUpdateProductSimilarities(List<Long> productIds);

    /**
     * 计算指定商品与其他所有商品的相似度
     * 适用于新增商品或商品信息有较大变更时调用
     *
     * @param productId 商品ID
     * @return 计算更新的相似度对数量
     */
    int calculateAndUpdateSimilaritiesForProduct(Long productId);

    /**
     * 获取与商品最相似的N个商品
     *
     * @param productId 商品ID
     * @param limit     数量限制
     * @return 相似商品列表
     */
    List<Product> getMostSimilarProducts(Long productId, int limit);
}
