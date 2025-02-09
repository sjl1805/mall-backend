package com.example.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 推荐商品表
 *
 * @TableName recommend_product
 */
@TableName(value = "recommend_product", autoResultMap = true)
@Data
public class RecommendProduct implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
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
    private Integer status;
    /**
     * 开始时间
     */
    private Date startTime;
    /**
     * 结束时间
     */
    private Date endTime;
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