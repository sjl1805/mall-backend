package com.example.model.dto.coupon;

import com.example.model.enums.CouponStatusEnum;
import lombok.Data;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "优惠券更新请求参数")
@Data
public class CouponUpdateDTO {
    @Schema(description = "优惠券名称", maxLength = 32, example = "新用户专享券（更新）")
    @Size(max = 32, message = "名称最长32个字符")
    private String name;

    @Schema(description = "面值（满减金额/折扣率）", example = "60.00")
    private BigDecimal value;

    @Schema(description = "最低消费金额", example = "150.00")
    private BigDecimal minAmount;

    @Schema(description = "生效时间", example = "2024-03-20 00:00:00")
    private LocalDateTime startTime;

    @Schema(description = "失效时间", example = "2024-04-20 23:59:59")
    private LocalDateTime endTime;

    @Schema(description = "优惠券状态", implementation = CouponStatusEnum.class)
    private CouponStatusEnum status;
} 