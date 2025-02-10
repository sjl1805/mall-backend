package com.example.model.dto.order;

import com.example.model.enums.OrderStatusEnum;
import lombok.Data;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "订单分页查询参数")
@Data
public class OrderPageQueryDTO {
    @Schema(description = "订单编号（模糊查询）", example = "20240315")
    private String orderNo;
    
    @Schema(description = "订单状态", implementation = OrderStatusEnum.class)
    private OrderStatusEnum status;
    
    @Schema(description = "创建时间起始", example = "2024-03-01 00:00:00")
    private LocalDateTime createTimeStart;
    
    @Schema(description = "创建时间结束", example = "2024-03-31 23:59:59")
    private LocalDateTime createTimeEnd;
    
    @Schema(description = "是否包含已取消订单", example = "false")
    private Boolean includeCanceled = false;
} 