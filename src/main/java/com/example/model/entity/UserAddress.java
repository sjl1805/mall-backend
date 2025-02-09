package com.example.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户收货地址表
 *
 * @TableName user_address
 */
@TableName(value = "user_address", autoResultMap = true)
@Data
public class UserAddress implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     *
     */
    private Long userId;
    /**
     * 默认地址状态：0-非默认 1-默认
     */
    private Integer isDefault;
    /**
     *
     */
    private Integer isDefaultTrue;
    /**
     *
     */
    private String receiverName;
    /**
     *
     */
    private String receiverPhone;
    /**
     *
     */
    private String province;
    /**
     *
     */
    private String city;
    /**
     *
     */
    private String district;
    /**
     *
     */
    private String detailAddress;
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