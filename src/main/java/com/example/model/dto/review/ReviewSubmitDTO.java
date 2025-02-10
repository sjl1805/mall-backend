package com.example.model.dto.review;

import com.example.model.enums.RatingEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Schema(description = "提交评价请求参数")
@Data
public class ReviewSubmitDTO {
    @Schema(description = "订单ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "6001")
    @NotNull(message = "订单ID不能为空")
    private Long orderId;

    @Schema(description = "商品ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "3001")
    @NotNull(message = "商品ID不能为空")
    private Long productId;

    @Schema(description = "评分等级", requiredMode = Schema.RequiredMode.REQUIRED, implementation = RatingEnum.class)
    @NotNull(message = "评分不能为空")
    private RatingEnum rating;

    @Schema(description = "评价内容", maxLength = 500, example = "非常满意！")
    @Size(max = 500, message = "评价内容最长500个字符")
    private String content;

    @Schema(description = "评价图片列表", example = "['image1.jpg','image2.jpg']")
    private List<String> images; // 图片URL列表
} 