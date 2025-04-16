package com.example.model.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户行为视图对象
 */
@Data
public class UserBehaviorVO {
    /**
     * 行为ID
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
     * 商品名称
     */
    private String productName;

    /**
     * 商品图片
     */
    private String productImage;

    /**
     * 商品价格
     */
    private BigDecimal productPrice;

    /**
     * 行为类型：1-浏览，2-收藏，3-加购，4-购买，5-评价
     */
    private Integer behaviorType;

    /**
     * 行为类型描述
     */
    private String behaviorTypeDesc;

    /**
     * 行为时间
     */
    private Date behaviorTime;

    /**
     * 格式化的行为时间
     */
    private String behaviorTimeStr;

    /**
     * 创建时间
     */
    private Date createTime;
}