package com.example.model.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 评价数据传输对象
 */
@Data
public class ReviewDTO {
    /**
     * 商品ID
     */
    @NotNull(message = "商品ID不能为空")
    private Long productId;

    /**
     * 订单ID
     */
    private Long orderId;

    /**
     * 评分：1-5
     */
    @NotNull(message = "评分不能为空")
    @Min(value = 1, message = "评分最小为1")
    @Max(value = 5, message = "评分最大为5")
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
} 