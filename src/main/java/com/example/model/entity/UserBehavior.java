package com.example.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import com.example.model.enums.UserBehaviorTypeEnum;
/**
 * 用户行为记录表
 *
 * @TableName user_behavior
 */
@TableName(value = "user_behavior", autoResultMap = true)
@Data
@EqualsAndHashCode(callSuper = true)
public class UserBehavior extends BaseEntity {
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
    private UserBehaviorTypeEnum behaviorType;
    /**
     * 行为时间
     */
    private LocalDateTime behaviorTime;
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

}