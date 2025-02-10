package com.example.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.model.enums.FavoriteFolderStatusEnum;

import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 收藏夹表
 *
 * @TableName favorite_folder
 */
@TableName(value = "favorite_folder", autoResultMap = true)
@Data
@EqualsAndHashCode(callSuper = true)
public class FavoriteFolder extends BaseEntity {
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
    private FavoriteFolderStatusEnum isPublic;
    /**
     * 收藏项数量
     */
    private Integer itemCount;
    /**
     * 排序
     */
    private Integer sort;

}