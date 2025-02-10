package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.model.dto.review.AdminReviewDTO;
import com.example.model.dto.review.ReviewDetailDTO;
import com.example.model.dto.review.ReviewPageQueryDTO;
import com.example.model.entity.ProductReview;
import com.example.model.enums.ReviewStatusEnum;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author 31815
 * @description 针对表【product_review(商品评价表)】的数据库操作Mapper
 * @createDate 2025-02-10 02:09:03
 * @Entity gg.model.ProductReview
 */
@Mapper
public interface ProductReviewMapper extends BaseMapper<ProductReview> {

    /**
     * 分页查询评价（管理员用）
     */
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
    List<AdminReviewDTO> selectAdminReviewList(Page<AdminReviewDTO> page,
                                             @Param("query") ReviewPageQueryDTO query);

    /**
     * 更新评价审核状态
     */
    @Update("UPDATE product_review SET status = #{status}, audit_remark = #{auditRemark}, audit_time = NOW() " +
            "WHERE id = #{reviewId}")
    int updateReviewStatus(@Param("reviewId") Long reviewId,
                         @Param("status") ReviewStatusEnum status,
                         @Param("auditRemark") String auditRemark);

    /**
     * 获取评价详情
     */
    @Select("SELECT pr.id AS reviewId, u.username AS userName, p.name AS productName, " +
            "pr.rating, pr.content, pr.images, pr.status AS statusDesc, " +
            "pr.create_time, pr.audit_time " +
            "FROM product_review pr " +
            "JOIN users u ON pr.user_id = u.id " +
            "JOIN products p ON pr.product_id = p.id " +
            "WHERE pr.id = #{reviewId}")
    ReviewDetailDTO selectReviewDetail(@Param("reviewId") Long reviewId);

    /**
     * 统计用户未审核评价数量
     */
    @Select("SELECT COUNT(*) FROM product_review WHERE status = 'PENDING'")
    int countPendingReviews();

    /**
     * 批量删除评价
     */
    @Update("<script>" +
            "DELETE FROM product_review " +
            "WHERE id IN " +
            "<foreach collection='reviewIds' item='id' open='(' separator=',' close=')'>" +
            "#{id}" +
            "</foreach>" +
            "</script>")
    int batchDeleteReviews(@Param("reviewIds") List<Long> reviewIds);
}





