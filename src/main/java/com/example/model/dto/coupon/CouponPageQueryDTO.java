package com.example.model.dto.coupon;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.model.enums.UserCouponStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Schema(description = "优惠券分页查询参数")
@Data
@EqualsAndHashCode(callSuper = true)
public class CouponPageQueryDTO extends Page<AdminCouponDTO> {
    @Schema(description = "优惠券名称（模糊查询）", example = "新用户")
    private String couponName;
    @Schema(description = "优惠券状态", implementation = UserCouponStatusEnum.class)
    private UserCouponStatusEnum status;
    @Schema(description = "是否已过期", example = "false")
    private Boolean expired;

} 