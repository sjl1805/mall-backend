package com.example.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户行为记录表
 *
 * @TableName user_behavior
 */
@TableName(value = "user_behavior", autoResultMap = true)
@Data
public class UserBehavior implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 行为记录ID
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
     * 行为类型：1-浏览 2-收藏 3-购买
     */
    private Integer behaviorType;
    /**
     * 行为时间
     */
    private Date behaviorTime;
    /**
     * 停留时长（秒）
     */
    private Integer duration;
    /**
     * 设备类型
     */
    private String deviceType;
    /**
     * 地理位置
     */
    private String location;
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