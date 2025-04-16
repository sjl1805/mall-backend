package com.example.model.vo;

import lombok.Data;

import java.util.Date;

/**
 * 评价视图对象
 */
@Data
public class ReviewVO {
    /**
     * 评价ID
     */
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
     * 订单ID
     */
    private Long orderId;

    /**
     * 评分：1-5
     */
    private Integer rating;

    /**
     * 评价内容
     */
    private String content;

    /**
     * 是否匿名：0-否，1-是
     */
    private Integer anonymous;

    /**
     * 评价图片，多个图片以逗号分隔
     */
    private String images;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品图片
     */
    private String productImage;

    /**
     * 格式化后的时间
     */
    private String createTimeStr;
} 