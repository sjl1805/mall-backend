package com.example.model.vo.coupon;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Schema(name = "优惠券创建VO")
public class CouponCreateVO {
    @Schema(name = "name", description = "优惠券名称", required = true, example = "春季大促券")
    @NotBlank(message = "优惠券名称不能为空")
    @Size(max = 32, message = "名称最长32个字符")
    private String name;

    @Schema(name = "type", description = "优惠券类型", required = true, example = "1", allowableValues = {"1", "2"})
    @NotNull(message = "优惠券类型不能为空")
    @Min(1)
    @Max(2)
    private Integer type;

    @Schema(name = "value", description = "优惠值", required = true, example = "100.00")
    @NotNull(message = "优惠值不能为空")
    @DecimalMin("0.01")
    private BigDecimal value;

    @Schema(name = "minAmount", description = "使用门槛", required = true, example = "500.00")
    @NotNull(message = "使用门槛不能为空")
    @DecimalMin("0.00")
    private BigDecimal minAmount;

    @Schema(name = "startTime", description = "开始时间", required = true, example = "2024-03-20T00:00:00")
    @NotNull(message = "开始时间不能为空")
    private LocalDateTime startTime;

    @Schema(name = "endTime", description = "结束时间", required = true, example = "2024-04-20T23:59:59")
    @NotNull(message = "结束时间不能为空")
    private LocalDateTime endTime;

    @Schema(name = "categoryIds", description = "适用分类ID列表", example = "[1001, 1002]")
    private List<Long> categoryIds;

    @Schema(name = "productIds", description = "指定商品ID列表", example = "[2001, 2002]")
    private List<Long> productIds;

    @Schema(name = "userLimit", description = "每人限领数量", example = "2")
    @Min(1)
    private Integer userLimit = 1;
} 