package com.example.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.model.enums.UserAddressStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户收货地址表
 *
 * @TableName user_address
 */
@TableName(value = "user_address", autoResultMap = true)
@Data
@EqualsAndHashCode(callSuper = true)
public class UserAddress extends BaseEntity {
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
    private UserAddressStatusEnum isDefault;
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
}