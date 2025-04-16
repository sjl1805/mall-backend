package com.example.controller;

import com.example.common.Result;
import com.example.model.entity.Category;
import com.example.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.example.annotation.RequiresRole;
import javax.validation.Valid;
import java.util.List;

/**
 * 分类管理控制器
 */
@Slf4j
@RestController
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
@RequiresRole(1) // 管理员角色值为1 
public class AdminCategoryController {

    private final CategoryService categoryService;

    /**
     * 获取分类树形列表
     *
     * @return 分类树
     */
    @GetMapping("/tree")
    public Result<List<Category>> getCategoryTree() {
        List<Category> categoryTree = categoryService.getCategoryTree();
        return Result.success(categoryTree);
    }

    /**
     * 获取所有分类的平铺列表
     *
     * @return 分类列表
     */
    @GetMapping
    public Result<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.list();
        return Result.success(categories);
    }

    /**
     * 获取分类详情
     *
     * @param categoryId 分类ID
     * @return 分类详情
     */
    @GetMapping("/{categoryId}")
    public Result<Category> getCategoryDetail(@PathVariable Long categoryId) {
        Category category = categoryService.getById(categoryId);
        return Result.success(category);
    }

    /**
     * 添加分类
     *
     * @param category 分类信息
     * @return 添加结果
     */
    @PostMapping
    public Result<Boolean> addCategory(@RequestBody @Valid Category category) {
        // 设置默认值
        if (category.getParentId() == null) {
            category.setParentId(0L); // 默认为一级分类
        }
        if (category.getLevel() == null) {
            category.setLevel(category.getParentId() == 0L ? 1 : 2); // 根据父ID设置层级
        }
        if (category.getStatus() == null) {
            category.setStatus(1); // 默认启用
        }

        boolean result = categoryService.save(category);
        return Result.success(result, "添加分类成功");
    }

    /**
     * 更新分类
     *
     * @param category 分类信息
     * @return 更新结果
     */
    @PutMapping
    public Result<Boolean> updateCategory(@RequestBody @Valid Category category) {
        if (category.getId() == null) {
            return Result.error("分类ID不能为空");
        }

        boolean result = categoryService.updateById(category);
        return Result.success(result, "更新分类成功");
    }

    /**
     * 删除分类
     *
     * @param categoryId 分类ID
     * @return 删除结果
     */
    @DeleteMapping("/{categoryId}")
    public Result<Boolean> deleteCategory(@PathVariable Long categoryId) {
        // 检查是否有子分类
        List<Category> children = categoryService.getChildrenCategories(categoryId);
        if (!children.isEmpty()) {
            return Result.error("该分类下有子分类，无法删除");
        }

        boolean result = categoryService.removeById(categoryId);
        return Result.success(result, "删除分类成功");
    }

    /**
     * 启用/禁用分类
     *
     * @param categoryId 分类ID
     * @param status    状态：0-禁用，1-正常
     * @return 操作结果
     */
    @PutMapping("/{categoryId}/status")
    public Result<Boolean> updateCategoryStatus(
            @PathVariable Long categoryId,
            @RequestParam Integer status) {

        Category category = new Category();
        category.setId(categoryId);
        category.setStatus(status);

        boolean result = categoryService.updateById(category);
        String statusText = status == 1 ? "启用" : "禁用";
        return Result.success(result, "分类" + statusText + "成功");
    }

    /**
     * 上传分类图标
     *
     * @param file       图标文件
     * @param categoryId 分类ID
     * @return 图标URL
     */
    @PostMapping("/icon")
    public Result<String> uploadCategoryIcon(
            @RequestParam("file") MultipartFile file,
            @RequestParam("categoryId") Long categoryId) {

        if (file.isEmpty()) {
            return Result.error("请选择图标文件");
        }

        String iconUrl = categoryService.uploadIcon(file, categoryId);
        return Result.success(iconUrl, "上传图标成功");
    }

    /**
     * 修改分类排序
     *
     * @param categoryId 分类ID
     * @param sort      排序值
     * @return 操作结果
     */
    @PutMapping("/{categoryId}/sort")
    public Result<Boolean> updateCategorySort(
            @PathVariable Long categoryId,
            @RequestParam Integer sort) {

        boolean result = categoryService.updateCategorySort(categoryId, sort);
        return Result.success(result, "修改排序成功");
    }

    /**
     * 移动分类
     *
     * @param categoryId     分类ID
     * @param targetParentId 目标父分类ID
     * @return 操作结果
     */
    @PutMapping("/{categoryId}/move")
    public Result<Boolean> moveCategory(
            @PathVariable Long categoryId,
            @RequestParam Long targetParentId) {

        boolean result = categoryService.moveCategory(categoryId, targetParentId);
        return Result.success(result, "移动分类成功");
    }

    /**
     * 批量添加分类
     *
     * @param categories 分类列表
     * @return 操作结果
     */
    @PostMapping("/batch")
    public Result<Boolean> batchAddCategories(@RequestBody List<Category> categories) {
        boolean result = categoryService.batchAddCategories(categories);
        return Result.success(result, "批量添加分类成功");
    }
} 