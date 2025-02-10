package com.example.model.dto.coupon;

import com.example.model.enums.CouponStatusEnum;
import lombok.Data;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CouponUpdateDTO {
    @Size(max = 32, message = "名称最长32个字符")
    private String name;

    private BigDecimal value;
    private BigDecimal minAmount;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private CouponStatusEnum status;
} 