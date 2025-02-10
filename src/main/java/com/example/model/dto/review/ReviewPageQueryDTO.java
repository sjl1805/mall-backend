package com.example.model.dto.review;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.model.enums.ReviewStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Schema(description = "评价分页查询参数")
@Data
@EqualsAndHashCode(callSuper = true)
public class ReviewPageQueryDTO extends Page<AdminReviewDTO> {
    @Schema(description = "商品ID", example = "3001")
    private Long productId;

    @Schema(description = "关键词（商品名称/评价内容）", example = "手机")
    private String keyword;
    @Schema(description = "评价状态", implementation = ReviewStatusEnum.class)
    private ReviewStatusEnum status;

    @Schema(description = "是否包含图片", example = "true")
    private Boolean hasImage;
} 