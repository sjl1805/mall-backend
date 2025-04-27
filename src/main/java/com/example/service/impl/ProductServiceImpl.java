package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.ResultCode;
import com.example.exception.BusinessException;
import com.example.mapper.ProductMapper;
import com.example.model.entity.Product;
import com.example.service.ProductService;
import com.example.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author 28619
 * @description 针对表【product(商品表)】的数据库操作Service实现
 * @createDate 2025-04-09 17:42:33
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product>
        implements ProductService {

    private final FileUtil fileUtil;


    @Override
    @Cacheable(value = "product", key = "'page:category:' + #categoryId + ':keyword:' + #keyword + ':minPrice:' + #minPrice + ':maxPrice:' + #maxPrice + ':page:' + #page + ':size:' + #size")
    public Page<Product> getProductPage(long page, long size, Long categoryId, String keyword,
                                        Double minPrice, Double maxPrice) {
        // 参数验证
        if (page <= 0) {
            page = 1;
        }
        if (size <= 0) {
            size = 10;
        }

        // 构建查询条件
        LambdaQueryWrapper<Product> queryWrapper = new LambdaQueryWrapper<>();

        // 状态为上架的商品
        queryWrapper.eq(Product::getStatus, 1);


        // 按分类筛选
        if (categoryId != null && categoryId > 0) {
            queryWrapper.eq(Product::getCategoryId, categoryId);
        }

        // 按关键词筛选（商品名称或描述）
        if (StringUtils.hasText(keyword)) {
            queryWrapper.and(wrapper -> wrapper
                    .like(Product::getName, keyword)
                    .or()
                    .like(Product::getDescription, keyword));
        }

        // 按价格范围筛选
        if (minPrice != null && minPrice >= 0) {
            queryWrapper.ge(Product::getPrice, minPrice);
        }
        if (maxPrice != null && maxPrice > 0) {
            queryWrapper.le(Product::getPrice, maxPrice);
        }

        // 按创建时间倒序
        queryWrapper.orderByDesc(Product::getCreateTime);

        // 分页查询
        return page(new Page<>(page, size), queryWrapper);
    }

    @Override
    @Cacheable(value = "product", key = "'hot:' + #limit")
    public List<Product> getHotProducts(int limit) {
        // 获取销量前N的商品
        LambdaQueryWrapper<Product> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Product::getStatus, 1)  // 状态为上架
                .orderByDesc(Product::getSales) // 按销量降序
                .last("LIMIT " + limit);        // 限制结果数量

        return list(queryWrapper);
    }

    @Override
    @Cacheable(value = "product", key = "'new:' + #limit")
    public List<Product> getNewProducts(int limit) {
        // 获取最新上架的商品
        LambdaQueryWrapper<Product> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Product::getStatus, 1)  // 状态为上架
                .orderByDesc(Product::getCreateTime) // 按创建时间降序
                .last("LIMIT " + limit);        // 限制结果数量

        return list(queryWrapper);
    }

    @Override
    @Cacheable(value = "product", key = "'category:' + #categoryId + ':limit:' + #limit")
    public List<Product> getProductsByCategory(Long categoryId, int limit) {
        if (categoryId == null || categoryId <= 0) {
            throw new BusinessException("分类ID不合法", ResultCode.PARAM_ERROR);
        }

        // 获取指定分类下的商品
        LambdaQueryWrapper<Product> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Product::getCategoryId, categoryId) // 指定分类
                .eq(Product::getStatus, 1)     // 状态为上架
                .orderByDesc(Product::getCreateTime) // 按创建时间降序
                .last("LIMIT " + limit);       // 限制结果数量

        return list(queryWrapper);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "product", allEntries = true)
    public boolean increaseSales(Long productId, int quantity) {
        if (productId == null) {
            throw new BusinessException("商品ID不能为空", ResultCode.PARAM_ERROR);
        }

        LambdaUpdateWrapper<Product> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Product::getId, productId);

        if (quantity > 0) {
            // 增加销量
            updateWrapper.setSql("sales = sales + " + quantity);
        } else if (quantity < 0) {
            // 减少销量（负的quantity表示减少销量）
            int absQuantity = Math.abs(quantity);

            // 获取当前销量，确保不会减少到负数
            Product product = this.getById(productId);
            if (product == null) {
                throw new BusinessException("商品不存在", ResultCode.PARAM_ERROR);
            }

            if (product.getSales() < absQuantity) {
                absQuantity = product.getSales(); // 最多减到0
            }

            updateWrapper.setSql("sales = sales - " + absQuantity);
        }

        return this.update(updateWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "product", allEntries = true)
    public boolean decreaseStock(Long productId, int quantity) {
        if (productId == null) {
            throw new BusinessException("商品ID不能为空", ResultCode.PARAM_ERROR);
        }

        // 获取商品信息
        Product product = this.getById(productId);
        if (product == null) {
            throw new BusinessException("商品不存在", ResultCode.PARAM_ERROR);
        }

        LambdaUpdateWrapper<Product> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Product::getId, productId);

        if (quantity > 0) {
            // 减少库存
            if (product.getStock() < quantity) {
                throw new BusinessException("商品库存不足", ResultCode.PARAM_ERROR);
            }

            updateWrapper.ge(Product::getStock, quantity); // 确保库存充足
            updateWrapper.setSql("stock = stock - " + quantity);
        } else if (quantity < 0) {
            // 增加库存（负的quantity表示增加库存）
            updateWrapper.setSql("stock = stock + " + Math.abs(quantity));
        } else {
            // quantity为0，不做任何操作
            return true;
        }

        return this.update(updateWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "product", key = "'detail:' + #productId")
    public String uploadImage(MultipartFile file, Long productId) {
        if (file == null || file.isEmpty()) {
            throw new BusinessException("文件不能为空", ResultCode.PARAM_ERROR);
        }

        if (productId == null) {
            throw new BusinessException("商品ID不能为空", ResultCode.PARAM_ERROR);
        }

        // 验证商品是否存在
        Product product = this.getById(productId);
        if (product == null) {
            throw new BusinessException("商品不存在", ResultCode.PARAM_ERROR);
        }

        // 上传图片
        String imageUrl = fileUtil.uploadFile(file);

        // 更新商品主图
        product.setImage(imageUrl);
        this.updateById(product);

        return imageUrl;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "product", key = "'detail:' + #productId")
    public String uploadProductImage(MultipartFile file, Long productId) {
        if (file == null || file.isEmpty()) {
            throw new BusinessException("文件不能为空", ResultCode.PARAM_ERROR);
        }

        if (productId == null) {
            throw new BusinessException("商品ID不能为空", ResultCode.PARAM_ERROR);
        }

        // 验证商品是否存在
        Product product = this.getById(productId);
        if (product == null) {
            throw new BusinessException("商品不存在", ResultCode.PARAM_ERROR);
        }

        // 上传图片
        String imageUrl = fileUtil.uploadFile(file);

        // 更新商品图片集
        String images = product.getImages();
        if (StringUtils.hasText(images)) {
            images = images + "," + imageUrl;
        } else {
            images = imageUrl;
        }
        product.setImages(images);
        this.updateById(product);

        return imageUrl;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "product", key = "'detail:' + #productId")
    public boolean deleteProductImage(Long productId, String imageUrl) {
        if (productId == null || !StringUtils.hasText(imageUrl)) {
            throw new BusinessException("参数错误", ResultCode.PARAM_ERROR);
        }

        // 验证商品是否存在
        Product product = this.getById(productId);
        if (product == null) {
            throw new BusinessException("商品不存在", ResultCode.PARAM_ERROR);
        }

        // 从图片集中删除指定图片
        String images = product.getImages();
        if (!StringUtils.hasText(images)) {
            return true; // 没有图片，视为删除成功
        }

        String[] imageArray = images.split(",");
        StringBuilder newImages = new StringBuilder();
        for (String img : imageArray) {
            if (!img.equals(imageUrl)) {
                if (newImages.length() > 0) {
                    newImages.append(",");
                }
                newImages.append(img);
            }
        }

        // 更新商品图片集
        product.setImages(newImages.toString());
        boolean result = this.updateById(product);

        // 删除图片文件
        if (result) {
            fileUtil.deleteFile(imageUrl);
        }

        return result;
    }

    /**
     * 获取商品详情并记录用户浏览行为
     *
     * @param id     商品ID
     * @param userId 用户ID，可为null（未登录）
     * @return 商品详情
     */
    @Override
    @Cacheable(value = "product", key = "'detail:' + #id")
    public Product getProductDetailAndRecordView(Long id, Long userId) {
        // 获取商品详情
        Product product = this.getById(id);

        return product;
    }

    /**
     * 批量更新商品状态
     *
     * @param productIds 商品ID列表
     * @param status     状态：0-下架，1-上架
     * @return 是否全部成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "product", allEntries = true)
    public boolean batchUpdateProductStatus(List<Long> productIds, Integer status) {
        if (productIds == null || productIds.isEmpty()) {
            return false;
        }

        if (status == null || (status != 0 && status != 1)) {
            throw new BusinessException("商品状态值不正确", ResultCode.PARAM_ERROR);
        }

        // 批量更新商品状态
        return lambdaUpdate()
                .in(Product::getId, productIds)
                .set(Product::getStatus, status)
                .update();
    }

    @Override
    @Cacheable(value = "product", key = "'lowStock:' + #threshold")
    public long countLowStockProducts(int threshold) {
        // 统计库存低于阈值的商品数量
        return lambdaQuery()
                .lt(Product::getStock, threshold)
                .eq(Product::getStatus, 1) // 只统计上架商品
                .count();
    }

    @Override
    @Cacheable(value = "product", key = "'active:count'")
    public long countActiveProducts() {
        // 统计上架中的商品数量
        return lambdaQuery()
                .eq(Product::getStatus, 1) // 状态为上架
                .count();
    }
}




