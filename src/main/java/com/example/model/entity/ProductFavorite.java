package com.example.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 商品收藏表
 *
 * @TableName product_favorite
 */
@TableName(value = "product_favorite", autoResultMap = true)
@Data
@EqualsAndHashCode(callSuper = true)
public class ProductFavorite extends BaseEntity {
    /**
     * 收藏ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 商品ID
     */
    private Long productId;
    /**
     * 收藏夹ID（NULL表示未分类）
     */
    private Long folderId;
}