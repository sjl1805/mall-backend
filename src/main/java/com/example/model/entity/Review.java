package com.example.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户评价表
 *
 * @TableName review
 */
@TableName(value = "review")
@Data
public class Review implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 评价ID
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
     * 订单ID
     */
    private Long orderId;
    /**
     * 评价内容
     */
    private String content;
    /**
     * 评分：1-5星
     */
    private Integer rating;
    /**
     * 评价图片
     */
    private String images;
    /**
     * 是否匿名：0-否，1-是
     */
    private Integer anonymous;
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

}