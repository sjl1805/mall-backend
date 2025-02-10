package com.example.model.dto.coupon;

import com.example.model.enums.CouponStatusEnum;
import com.example.model.enums.CouponTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "管理员视角优惠券详情")
@Data
public class AdminCouponDTO {
    @Schema(description = "优惠券ID", example = "2001")
    private Long id;

    @Schema(description = "优惠券名称", example = "新用户专享券")
    private String name;

    @Schema(description = "优惠券类型", implementation = CouponTypeEnum.class)
    private CouponTypeEnum type;

    @Schema(description = "优惠金额/折扣", example = "50.00")
    private BigDecimal value;

    @Schema(description = "最低消费金额", example = "100.00")
    private BigDecimal minAmount;

    @Schema(description = "生效时间", example = "2024-03-15 00:00:00")
    private LocalDateTime startTime;

    @Schema(description = "失效时间", example = "2024-04-15 23:59:59")
    private LocalDateTime endTime;

    @Schema(description = "优惠券状态", implementation = CouponStatusEnum.class)
    private CouponStatusEnum status;

    @Schema(description = "创建时间", example = "2024-03-01 10:00:00")
    private LocalDateTime createTime;

    @Schema(description = "更新时间", example = "2024-03-01 10:05:00")
    private LocalDateTime updateTime;

    @Schema(description = "已发放数量", example = "1000")
    private Integer totalIssued;

    @Schema(description = "已使用数量", example = "500")
    private Integer usedCount;

    @Schema(description = "用户ID", example = "12345")
    private Long userId;

    @Schema(description = "用户名", example = "john_doe")
    private String userName;

    @Schema(description = "优惠券名称（冗余字段）", example = "新用户专享券")
    private String couponName;

    @Schema(description = "状态描述", example = "已生效")
    private String statusDesc;

    @Schema(description = "领取时间", example = "2024-03-15 10:00:00")
    private LocalDateTime getTime;

    @Schema(description = "使用时间", example = "2024-03-16 15:30:00")
    private LocalDateTime useTime;

    @Schema(description = "订单ID", example = "6001")
    private Long orderId;
} 