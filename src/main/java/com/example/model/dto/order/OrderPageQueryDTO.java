package com.example.model.dto.order;

import com.example.model.enums.OrderStatusEnum;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class OrderPageQueryDTO {
    private String orderNo;
    private OrderStatusEnum status;
    private LocalDateTime createTimeStart;
    private LocalDateTime createTimeEnd;
    private Boolean includeCanceled = false;
} 