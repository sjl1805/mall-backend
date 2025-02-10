package com.example.model.vo.coupon;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
@Schema(name = "优惠券统计VO")
public class CouponStatsVO {
    @Schema(name = "totalGrant", description = "发放总数", example = "1000")
    private Integer totalGrant;

    @Schema(name = "usedCount", description = "已使用数量", example = "500")
    private Integer usedCount;

    @Schema(name = "usageRate", description = "使用率", example = "0.50")
    private BigDecimal usageRate;

    @Schema(name = "totalDiscount", description = "优惠总金额", example = "50000.00")
    private BigDecimal totalDiscount;

    @Schema(name = "grantTrend", description = "领取趋势数据", example = "{\"2024-03\": 200}")
    private Map<String, Integer> grantTrend;
} 