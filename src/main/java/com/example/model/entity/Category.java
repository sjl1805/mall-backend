package com.example.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 商品分类表
 *
 * @TableName category
 */
@TableName(value = "category", autoResultMap = true)
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
    private Integer status;
    /**
     * 创建时间（带时区）
     */
    private Date createTime;
    /**
     * 更新时间（带时区）
     */
    private Date updateTime;
    /**
     * 乐观锁版本号
     */
    private Integer version;
    /**
     * 删除标志：0存在 1删除
     */
    private Integer deleted;
}