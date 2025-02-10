package com.example.model.vo.review;

import com.example.model.vo.user.UserSimpleVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(name = "评价详情VO")
public class ReviewDetailVO {
    @Schema(name = "reviewId", description = "评价ID", example = "5001")
    private Long reviewId;

    @Schema(name = "userInfo", description = "用户信息")
    private UserSimpleVO userInfo;

    @Schema(name = "rating", description = "评分", example = "5")
    private Integer rating;

    @Schema(name = "content", description = "评价内容", example = "商品质量很好")
    private String content;

    @Schema(name = "images", description = "评价图片")
    private List<String> images;

    @Schema(name = "likeCount", description = "点赞数", example = "10")
    private Integer likeCount;

    @Schema(name = "replies", description = "回复列表")
    private List<ReviewReplyVO> replies;

    @Schema(name = "isAnonymous", description = "是否匿名", example = "false")
    private Boolean isAnonymous;
} 