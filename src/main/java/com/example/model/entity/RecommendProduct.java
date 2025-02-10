package com.example.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.example.model.enums.RecommendProductStatusEnum;
/**
 * 推荐商品表
 *
 * @TableName recommend_product
 */
@TableName(value = "recommend_product", autoResultMap = true)
@Data
@EqualsAndHashCode(callSuper = true)
public class RecommendProduct extends BaseEntity {
    /**
     * 推荐商品ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 商品ID
     */
    private Long productId;
    /**
     * 推荐类型：1-热门商品 2-新品推荐
     */
    private Integer type;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 推荐状态：0-未生效 1-生效中
     */
    private RecommendProductStatusEnum status;
    /**
     * 开始时间
     */
    private LocalDateTime startTime;
    /**
     * 结束时间
     */
    private LocalDateTime endTime;
    /**
     * 算法版本
     */
    private String algorithmVersion;
    /**
     * 权重
     */
    private BigDecimal weight;
    /**
     * 推荐理由
     */
    private String recommendReason;

}