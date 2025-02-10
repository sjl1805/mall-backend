package com.example.model.vo.review;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
@Schema(name = "评价提交VO")
public class ReviewSubmitVO {
    @Schema(name = "orderItemId", description = "订单项ID", required = true, example = "2001")
    @NotNull(message = "订单项ID不能为空")
    private Long orderItemId;

    @Schema(name = "rating", description = "评分", required = true, example = "5", allowableValues = {"1", "2", "3", "4", "5"})
    @NotNull(message = "请选择评分")
    @Min(1)
    @Max(5)
    private Integer rating;

    @Schema(name = "content", description = "评价内容", example = "商品质量很好")
    @Size(max = 500, message = "评价内容最长500个字符")
    private String content;

    @Schema(name = "images", description = "评价图片", example = "[\"https://example.com/review1.jpg\"]")
    @Size(max = 3)
    private List<String> images;
} 