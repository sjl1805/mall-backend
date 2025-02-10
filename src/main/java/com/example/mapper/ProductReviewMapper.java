package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.model.dto.review.ReviewDetailDTO;
import com.example.model.dto.review.ReviewPageQueryDTO;
import com.example.model.entity.ProductReview;
import com.example.model.enums.ReviewStatusEnum;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

/**
 * @author 31815
 * @description 针对表【product_review(商品评价表)】的数据库操作Mapper
 * @createDate 2025-02-10 02:09:03
 * @Entity gg.model.ProductReview
 */
public interface ProductReviewMapper extends BaseMapper<ProductReview> {

    @Results(id = "adminReviewMap", value = {
            @Result(property = "userId", column = "user_id"),
            @Result(property = "userName", column = "user_name"),
            @Result(property = "productId", column = "product_id"),
            @Result(property = "productName", column = "product_name"),
            @Result(property = "auditRemark", column = "audit_remark"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "auditTime", column = "audit_time")
    })
    @Select("<script>" +
            "SELECT pr.id, pr.user_id AS userId, u.username AS userName, " +
            "pr.product_id AS productId, p.name AS productName, pr.rating, pr.content, " +
            "pr.audit_remark AS auditRemark, pr.create_time, pr.audit_time " +
            "FROM product_review pr " +
            "JOIN users u ON pr.user_id = u.id " +
            "JOIN products p ON pr.product_id = p.id " +
            "<where>" +
            "   <if test='query.productId != null'>AND pr.product_id = #{query.productId}</if>" +
            "   <if test='query.keyword != null'>" +
            "       AND (p.name LIKE CONCAT('%',#{query.keyword},'%') OR pr.content LIKE CONCAT('%',#{query.keyword},'%'))" +
            "   </if>" +
            "   <if test='query.status != null'>AND pr.status = #{query.status}</if>" +
            "   <if test='query.hasImage != null'>" +
            "       <if test='query.hasImage'>AND pr.images IS NOT NULL AND pr.images != ''</if>" +
            "       <if test='!query.hasImage'>AND (pr.images IS NULL OR pr.images = '')</if>" +
            "   </if>" +
            "</where>" +
            "ORDER BY pr.create_time DESC" +
            "</script>")
    ReviewPageQueryDTO selectAdminReviewList(@Param("query") ReviewPageQueryDTO query);


    /**
     * 更新评价审核状态
     */
    @Update("UPDATE product_review SET status = #{status}, audit_remark = #{auditRemark}, audit_time = NOW() " +
            "WHERE id = #{reviewId}")
    int updateReviewStatus(@Param("reviewId") Long reviewId,
                           @Param("status") ReviewStatusEnum status,
                           @Param("auditRemark") String auditRemark);

    @Options(useCache = true, flushCache = Options.FlushCachePolicy.FALSE)
    @Select("SELECT pr.id AS reviewId, u.username AS userName, p.name AS productName, " +
            "pr.rating, pr.content, pr.images, pr.status AS statusDesc, " +
            "pr.create_time, pr.audit_time " +
            "FROM product_review pr " +
            "JOIN users u ON pr.user_id = u.id " +
            "JOIN products p ON pr.product_id = p.id " +
            "WHERE pr.id = #{reviewId}")
    Optional<ReviewDetailDTO> selectReviewDetail(@Param("reviewId") Long reviewId);


    /**
     * 统计用户未审核评价数量
     */
    @Select("SELECT COUNT(*) FROM product_review WHERE status = 'PENDING'")
    int countPendingReviews();

    @Update("<script>" +
            "DELETE FROM product_review " +
            "WHERE id IN " +
            "<foreach collection='reviewIds' item='id' open='(' separator=',' close=')'>" +
            "#{id}" +
            "</foreach>" +
            " AND product_id IN (SELECT id FROM products WHERE merchant_id = #{merchantId})"
            + "</script>")
    int batchDeleteReviews(@Param("reviewIds") List<Long> reviewIds,
                           @Param("merchantId") Long merchantId);
}





