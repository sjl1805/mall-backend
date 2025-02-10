package com.example.model.dto.coupon;

import com.example.model.enums.CouponStatusEnum;
import com.example.model.enums.CouponTypeEnum;
import lombok.Data;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CouponCreateDTO {
    @NotBlank(message = "优惠券名称不能为空")
    @Size(max = 32, message = "名称最长32个字符")
    private String name;

    @NotNull(message = "优惠券类型不能为空")
    private CouponTypeEnum type;

    @NotNull(message = "面值不能为空")
    @DecimalMin(value = "0.01", message = "面值最小0.01元")
    private BigDecimal value;

    @NotNull(message = "使用门槛不能为空")
    @DecimalMin(value = "0.00", message = "门槛金额不能为负数")
    private BigDecimal minAmount;

    @Future(message = "生效时间必须是将来的时间")
    private LocalDateTime startTime;

    @Future(message = "失效时间必须是将来的时间")
    private LocalDateTime endTime;

    @NotNull(message = "状态不能为空")
    private CouponStatusEnum status;
} 