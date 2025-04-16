package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.ResultCode;
import com.example.exception.BusinessException;
import com.example.mapper.CategoryMapper;
import com.example.model.entity.Category;
import com.example.model.entity.Product;
import com.example.service.CategoryService;
import com.example.service.ProductService;
import com.example.util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 28619
 * @description 针对表【category(商品分类表)】的数据库操作Service实现
 * @createDate 2025-04-09 17:42:20
 */
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
        implements CategoryService {

    private final FileUtil fileUtil;
    private final ProductService productService;

    @Override
    public List<Category> getCategoryTree() {
        // 获取所有分类
        List<Category> allCategories = this.list();
        
        // 如果没有分类，返回空列表
        if (allCategories == null || allCategories.isEmpty()) {
            return new ArrayList<>();
        }

        // 按层级和排序字段排序
        allCategories.sort(Comparator.comparing(Category::getLevel)
                .thenComparing(Category::getSort, Comparator.nullsLast(Comparator.naturalOrder())));

        // 构建树形结构
        List<Category> root = new ArrayList<>();
        Map<Long, List<Category>> parentIdMap = allCategories.stream()
                .collect(Collectors.groupingBy(Category::getParentId));

        // 查找一级分类
        List<Category> firstLevel = parentIdMap.getOrDefault(0L, new ArrayList<>());
        root.addAll(firstLevel);

        // 递归构建子分类
        for (Category category : root) {
            buildChildrenCategories(category, parentIdMap);
        }

        return root;
    }

    /**
     * 递归构建子分类
     */
    private void buildChildrenCategories(Category parent, Map<Long, List<Category>> parentIdMap) {
        // 获取当前分类的子分类
        List<Category> children = parentIdMap.getOrDefault(parent.getId(), new ArrayList<>());

        // 按排序字段排序
        children.sort(Comparator.comparing(Category::getSort, Comparator.nullsLast(Comparator.naturalOrder())));

        // 设置到父分类的children属性
        if (!children.isEmpty()) {
            // 需要在Category类中添加children属性
            parent.setChildren(children);
            for (Category child : children) {
                buildChildrenCategories(child, parentIdMap);
            }
        }
    }

    @Override
    public List<Category> listEnabledCategories() {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getStatus, 1); // 1表示启用状态
        queryWrapper.orderByAsc(Category::getLevel);
        queryWrapper.orderByAsc(Category::getSort);
        List<Category> allEnabledCategories = this.list(queryWrapper);
        
        // 如果没有启用的分类，返回空列表
        if (allEnabledCategories == null || allEnabledCategories.isEmpty()) {
            return new ArrayList<>();
        }
        
        // 构建树形结构
        List<Category> root = new ArrayList<>();
        Map<Long, List<Category>> parentIdMap = allEnabledCategories.stream()
                .collect(Collectors.groupingBy(Category::getParentId));
                
        // 查找一级分类
        List<Category> firstLevel = parentIdMap.getOrDefault(0L, new ArrayList<>());
        root.addAll(firstLevel);
        
        // 递归构建子分类
        for (Category category : root) {
            buildChildrenCategories(category, parentIdMap);
        }
        
        return root;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String uploadIcon(MultipartFile file, Long categoryId) {
        // 验证分类是否存在
        Category category = this.getById(categoryId);
        if (category == null) {
            throw new BusinessException("分类不存在", ResultCode.PARAM_ERROR);
        }

        // 上传图标文件
        String iconUrl = fileUtil.uploadFile(file);

        // 如果分类已有图标，删除旧图标
        if (category.getIcon() != null && !category.getIcon().isEmpty()) {
            fileUtil.deleteFile(category.getIcon());
        }

        // 更新分类图标
        category.setIcon(iconUrl);
        this.updateById(category);

        return iconUrl;
    }

    @Override
    public List<Category> getChildrenCategories(Long parentId) {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getParentId, parentId);
        queryWrapper.eq(Category::getStatus, 1); // 只获取启用的分类
        queryWrapper.orderByAsc(Category::getSort); // 按排序字段升序排列
        List<Category> categories = this.list(queryWrapper);
        
        // 将null值的排序放到最后
        categories.sort(Comparator.comparing(Category::getSort, Comparator.nullsLast(Comparator.naturalOrder())));
        
        return categories;
    }

    @Override
    public boolean hasProducts(Long categoryId) {
        LambdaQueryWrapper<Product> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Product::getCategoryId, categoryId);
        queryWrapper.last("LIMIT 1"); // 只需要确认是否存在，返回一条记录即可
        return productService.count(queryWrapper) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchAddCategories(List<Category> categories) {
        if (categories == null || categories.isEmpty()) {
            return false;
        }

        // 设置默认值并验证
        for (Category category : categories) {
            if (category.getParentId() == null) {
                category.setParentId(0L);
            }

            if (category.getLevel() == null) {
                // 根据父ID推断层级
                if (category.getParentId() == 0L) {
                    category.setLevel(1);
                } else {
                    // 查询父分类
                    Category parent = getById(category.getParentId());
                    if (parent == null) {
                        throw new BusinessException("父分类不存在", ResultCode.PARAM_ERROR);
                    }
                    category.setLevel(parent.getLevel() + 1);
                }
            }

            if (category.getStatus() == null) {
                category.setStatus(1); // 默认启用
            }

            if (category.getSort() == null) {
                category.setSort(0); // 默认排序值
            }
        }

        return saveBatch(categories);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean moveCategory(Long categoryId, Long targetParentId) {
        // 验证分类和目标父分类
        Category category = getById(categoryId);
        if (category == null) {
            throw new BusinessException("分类不存在", ResultCode.PARAM_ERROR);
        }

        // 不能将分类移动到自己下面或者自己的子分类下面
        if (categoryId.equals(targetParentId)) {
            throw new BusinessException("不能将分类移动到自己下面", ResultCode.PARAM_ERROR);
        }

        if (targetParentId != 0) {
            Category targetParent = getById(targetParentId);
            if (targetParent == null) {
                throw new BusinessException("目标父分类不存在", ResultCode.PARAM_ERROR);
            }

            // 验证目标父分类不是当前分类的子分类
            if (isChildCategory(categoryId, targetParentId)) {
                throw new BusinessException("不能将分类移动到自己的子分类下面", ResultCode.PARAM_ERROR);
            }

            // 设置新的父分类和层级
            category.setParentId(targetParentId);
            category.setLevel(targetParent.getLevel() + 1);
        } else {
            // 移动到根级别
            category.setParentId(0L);
            category.setLevel(1);
        }

        // 更新当前分类
        boolean result = updateById(category);

        // 更新所有子分类的层级
        if (result) {
            updateChildrenLevel(categoryId, category.getLevel());
        }

        return result;
    }

    /**
     * 判断一个分类是否是另一个分类的子分类
     */
    private boolean isChildCategory(Long parentId, Long possibleChildId) {
        // 获取可能的子分类
        Category possibleChild = getById(possibleChildId);
        if (possibleChild == null) {
            return false;
        }

        // 如果直接父ID就是指定的parentId，则是子分类
        if (parentId.equals(possibleChild.getParentId())) {
            return true;
        }

        // 如果是根分类，则不是任何分类的子分类
        if (possibleChild.getParentId() == 0L) {
            return false;
        }

        // 递归检查父分类
        return isChildCategory(parentId, possibleChild.getParentId());
    }

    /**
     * 递归更新子分类的层级
     */
    private void updateChildrenLevel(Long parentId, Integer parentLevel) {
        // 获取直接子分类
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getParentId, parentId);
        List<Category> children = list(queryWrapper);

        if (!children.isEmpty()) {
            int newLevel = parentLevel + 1;

            for (Category child : children) {
                // 更新层级
                child.setLevel(newLevel);
                updateById(child);

                // 递归更新子分类的层级
                updateChildrenLevel(child.getId(), newLevel);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateCategorySort(Long categoryId, Integer sort) {
        Category category = getById(categoryId);
        if (category == null) {
            throw new BusinessException("分类不存在", ResultCode.PARAM_ERROR);
        }

        category.setSort(sort);
        return updateById(category);
    }
}




