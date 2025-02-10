package com.example.model.dto.coupon;

import com.example.model.enums.CouponStatusEnum;
import com.example.model.enums.CouponTypeEnum;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CouponPageQueryDTO {
    private String name;
    private CouponTypeEnum type;
    private CouponStatusEnum status;
    private LocalDateTime startTimeBegin;
    private LocalDateTime startTimeEnd;
} 