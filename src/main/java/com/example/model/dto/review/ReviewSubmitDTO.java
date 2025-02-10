package com.example.model.dto.review;

import com.example.model.enums.RatingEnum;
import lombok.Data;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

@Data
public class ReviewSubmitDTO {
    @NotNull(message = "订单ID不能为空")
    private Long orderId;

    @NotNull(message = "商品ID不能为空")
    private Long productId;

    @NotNull(message = "评分不能为空")
    private RatingEnum rating;

    @Size(max = 500, message = "评价内容最长500个字符")
    private String content;

    private List<String> images; // 图片URL列表
} 