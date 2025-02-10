package com.example.model.vo.recommend;

import com.example.model.vo.product.ProductBaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(name = "推荐商品VO")
public class RecommendProductVO {
    @Schema(name = "recommendId", description = "推荐ID", example = "2001")
    private Long recommendId;

    @Schema(name = "recommendType", description = "推荐类型", example = "hot", allowableValues = {"hot", "new", "similar"})
    private String recommendType;

    @Schema(name = "productInfo", description = "商品信息")
    private ProductBaseVO productInfo;

    @Schema(name = "recommendReason", description = "推荐理由", example = "同类热销商品")
    private String recommendReason;

    @Schema(name = "weight", description = "推荐权重", example = "0.95")
    private BigDecimal weight;
} 