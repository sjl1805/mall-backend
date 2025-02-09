package com.example.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 商品评价表
 *
 * @TableName product_review
 */
@TableName(value = "product_review", autoResultMap = true)
@Data
public class ProductReview implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
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
    private Integer rating;
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