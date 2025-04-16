package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.model.entity.Category;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author 28619
 * @description 针对表【category(商品分类表)】的数据库操作Service
 * @createDate 2025-04-09 17:42:20
 */
public interface CategoryService extends IService<Category> {
    /**
     * 获取商品分类树结构
     *
     * @return 分类树
     */
    List<Category> getCategoryTree();

    /**
     * 获取所有启用的分类
     *
     * @return 分类列表
     */
    List<Category> listEnabledCategories();

    /**
     * 上传分类图标
     *
     * @param file       图标文件
     * @param categoryId 分类ID
     * @return 图标URL
     */
    String uploadIcon(MultipartFile file, Long categoryId);

    /**
     * 根据父分类ID获取子分类列表
     *
     * @param parentId 父分类ID
     * @return 子分类列表
     */
    List<Category> getChildrenCategories(Long parentId);

    /**
     * 检查分类是否存在商品
     *
     * @param categoryId 分类ID
     * @return 是否存在商品
     */
    boolean hasProducts(Long categoryId);

    /**
     * 批量添加分类
     *
     * @param categories 分类列表
     * @return 是否成功
     */
    boolean batchAddCategories(List<Category> categories);

    /**
     * 移动分类
     *
     * @param categoryId     分类ID
     * @param targetParentId 目标父分类ID
     * @return 是否成功
     */
    boolean moveCategory(Long categoryId, Long targetParentId);

    /**
     * 更新分类排序
     *
     * @param categoryId 分类ID
     * @param sort       排序值
     * @return 是否成功
     */
    boolean updateCategorySort(Long categoryId, Integer sort);
}
