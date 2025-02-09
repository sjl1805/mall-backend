package com.example.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 收藏夹表
 *
 * @TableName favorite_folder
 */
@TableName(value = "favorite_folder", autoResultMap = true)
@Data
public class FavoriteFolder implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 收藏夹ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 收藏夹名称
     */
    private String name;
    /**
     * 收藏夹描述
     */
    private String description;
    /**
     * 公开状态：0-私密 1-公开
     */
    private Integer isPublic;
    /**
     * 排序
     */
    private Integer sort;
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