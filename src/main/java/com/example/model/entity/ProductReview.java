package com.example.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.model.enums.RatingEnum;
import com.example.model.enums.ReviewStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 商品评价表
 *
 * @TableName product_review
 */
@TableName(value = "product_review", autoResultMap = true)
@Data
@EqualsAndHashCode(callSuper = true)
public class ProductReview extends BaseEntity {
    /**
     * 评价ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 订单ID
     */
    private Long orderId;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 商品ID
     */
    private Long productId;
    /**
     * 评分：1-5分
     */
    private RatingEnum rating;
    /**
     * 评价内容
     */
    private String content;
    /**
     * 评价图片，JSON格式
     */
    private String images;
    /**
     * 审核状态：0-待审核 1-已通过 2-已拒绝
     */
    private ReviewStatusEnum status;

}