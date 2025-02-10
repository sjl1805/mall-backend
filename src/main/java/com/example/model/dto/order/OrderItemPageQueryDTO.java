package com.example.model.dto.order;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;


import java.math.BigDecimal;

@Schema(description = "订单项分页查询参数")
@Data
@EqualsAndHashCode(callSuper = true)
public class OrderItemPageQueryDTO extends Page<AdminOrderItemDTO> {
    @Schema(description = "订单编号", example = "20240315123456")
    private String orderNo;



    @Schema(description = "商品名称（模糊查询）", example = "手机")
    private String productName;

    @Schema(description = "最低价格", example = "100.00")
    private BigDecimal minPrice;

    @Schema(description = "最高价格", example = "5000.00")
    private BigDecimal maxPrice;
} 