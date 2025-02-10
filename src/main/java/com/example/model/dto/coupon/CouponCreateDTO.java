package com.example.model.dto.coupon;

import com.example.model.enums.CouponStatusEnum;
import com.example.model.enums.CouponTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "优惠券创建请求参数")
@Data
public class CouponCreateDTO {
    @Schema(description = "优惠券名称", requiredMode = Schema.RequiredMode.REQUIRED, maxLength = 32, example = "新用户专享券")
    @NotBlank(message = "优惠券名称不能为空")
    @Size(max = 32, message = "名称最长32个字符")
    private String name;

    @Schema(description = "优惠券类型", requiredMode = Schema.RequiredMode.REQUIRED, implementation = CouponTypeEnum.class)
    @NotNull(message = "优惠券类型不能为空")
    private CouponTypeEnum type;

    @Schema(description = "面值（满减金额/折扣率）", requiredMode = Schema.RequiredMode.REQUIRED, example = "50.00")
    @NotNull(message = "面值不能为空")
    @DecimalMin(value = "0.01", message = "面值最小0.01元")
    private BigDecimal value;

    @Schema(description = "最低消费金额", requiredMode = Schema.RequiredMode.REQUIRED, example = "100.00")
    @NotNull(message = "使用门槛不能为空")
    @DecimalMin(value = "0.00", message = "门槛金额不能为负数")
    private BigDecimal minAmount;

    @Schema(description = "生效时间", requiredMode = Schema.RequiredMode.REQUIRED, example = "2024-03-15 00:00:00")
    @Future(message = "生效时间必须是将来的时间")
    private LocalDateTime startTime;

    @Schema(description = "失效时间", requiredMode = Schema.RequiredMode.REQUIRED, example = "2024-04-15 23:59:59")
    @Future(message = "失效时间必须是将来的时间")
    private LocalDateTime endTime;

    @Schema(description = "初始状态", requiredMode = Schema.RequiredMode.REQUIRED, implementation = CouponStatusEnum.class)
    @NotNull(message = "状态不能为空")
    private CouponStatusEnum status;
} 