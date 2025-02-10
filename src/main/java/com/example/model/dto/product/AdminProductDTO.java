package com.example.model.dto.product;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "管理员视角商品详情")
@Data
public class AdminProductDTO {
    @Schema(description = "商品ID", example = "1001")
    private Long id;
    
    @Schema(description = "商品名称", example = "智能手机")
    private String name;
    
    @Schema(description = "分类名称", example = "数码产品")
    private String categoryName;
    
    @Schema(description = "商品价格", example = "2999.00")
    private BigDecimal price;
    
    @Schema(description = "商品库存", example = "100")
    private Integer stock;
    
    @Schema(description = "状态描述", example = "已上架")
    private String statusDesc;
    
    @Schema(description = "创建时间", example = "2024-03-01 10:00:00")
    private LocalDateTime createTime;
    
    @Schema(description = "更新时间", example = "2024-03-05 15:30:00")
    private LocalDateTime updateTime;
    
    @Schema(description = "销售总量", example = "500")
    private Integer salesVolume;
} 