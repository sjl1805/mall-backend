package com.example.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 商品分类表
 *
 * @TableName category
 */
@TableName(value = "category")
@Data
public class Category implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
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
     * 分类层级
     */
    private Integer level;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 图标
     */
    private String icon;
    /**
     * 状态：0-禁用，1-正常
     */
    private Integer status;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 子分类列表（非数据库字段）
     */
    @TableField(exist = false)
    private List<Category> children;
}