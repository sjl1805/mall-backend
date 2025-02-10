package com.example.model.dto.sku;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "管理员视角SKU详情")
@Data
public class AdminSkuDTO {
    @Schema(description = "SKU ID", example = "9001")
    private Long id;
    
    @Schema(description = "商品ID", example = "3001")
    private Long productId;
    
    @Schema(description = "商品名称", example = "智能手机")
    private String productName;
    
    @Schema(description = "规格值组合", example = "颜色:白色;内存:256GB")
    private String specValues;
    
    @Schema(description = "价格", example = "2999.00")
    private BigDecimal price;
    
    @Schema(description = "库存", example = "100")
    private Integer stock;
    
    @Schema(description = "销量", example = "500")
    private Integer sales;
    
    @Schema(description = "主图URL", example = "https://example.com/image.jpg")
    private String mainImage;
    
    @Schema(description = "状态描述", example = "已上架")
    private String statusDesc;
    
    @Schema(description = "创建时间", example = "2024-03-01 10:00:00")
    private LocalDateTime createTime;
    
    @Schema(description = "更新时间", example = "2024-03-05 15:30:00")
    private LocalDateTime updateTime;
} 