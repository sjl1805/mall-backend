package com.example.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.model.enums.CategoryStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 商品分类表
 *
 * @TableName category
 */
@TableName(value = "category", autoResultMap = true)
@Data
@EqualsAndHashCode(callSuper = true)
public class Category extends BaseEntity {
    /**
     * 分类ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 父分类ID
     */
    private Long parentId;
    /**
     * 分类名称
     */
    private String name;
    /**
     * 分类图标
     */
    private String icon;
    /**
     * 层级：1一级 2二级 3三级
     */
    private Integer level;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 状态：0-禁用 1-启用
     */
    private CategoryStatusEnum status;
}