package com.example.model.vo.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Schema(name = "订单概览VO")
public class OrderOverviewVO {
    @Schema(name = "orderId", description = "订单ID", example = "10001")
    private Long orderId;

    @Schema(name = "orderNo", description = "订单编号", example = "20240320123456")
    private String orderNo;

    @Schema(name = "status", description = "订单状态", example = "1", allowableValues = {"0", "1", "2", "3", "4"})
    private Integer status;

    @Schema(name = "payAmount", description = "支付金额", example = "2999.00")
    private BigDecimal payAmount;

    @Schema(name = "totalQuantity", description = "商品总数", example = "2")
    private Integer totalQuantity;

    @Schema(name = "productThumbnails", description = "商品缩略图列表", example = "[\"https://example.com/thumb1.jpg\",\"https://example.com/thumb2.jpg\"]")
    private List<String> productThumbnails;

    @Schema(name = "createTime", description = "创建时间", example = "2024-03-20T10:00:00")
    private LocalDateTime createTime;
} 