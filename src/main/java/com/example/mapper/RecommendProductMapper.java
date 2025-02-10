package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.model.dto.recommend.AdminRecommendDTO;
import com.example.model.dto.recommend.RecommendDetailDTO;
import com.example.model.dto.recommend.RecommendPageQueryDTO;

import com.example.model.entity.RecommendProduct;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import java.util.List;
/**
 * @author 31815
 * @description 针对表【recommend_product(推荐商品表)】的数据库操作Mapper
 * @createDate 2025-02-10 02:08:45
 * @Entity gg.model.RecommendProduct
 */
@Mapper
public interface RecommendProductMapper extends BaseMapper<RecommendProduct> {

    /**
     * 分页查询推荐商品（管理端）
     */
    @Select("<script>" +
            "SELECT rp.*, p.name AS product_name, " +
            "(CASE rp.type WHEN 1 THEN '热门商品' WHEN 2 THEN '新品推荐' END) AS type_desc, " +
            "(CASE rp.status WHEN 0 THEN '未生效' WHEN 1 THEN '生效中' END) AS status_desc " +
            "FROM recommend_product rp " +
            "JOIN products p ON rp.product_id = p.id " +
            "<where>" +
            "   <if test='query.productId != null'>AND rp.product_id = #{query.productId}</if>" +
            "   <if test='query.type != null'>AND rp.type = #{query.type.code}</if>" +
            "   <if test='query.status != null'>AND rp.status = #{query.status.code}</if>" +
            "   <if test='query.algorithmVersion != null'>AND rp.algorithm_version = #{query.algorithmVersion}</if>" +
            "   <if test='query.startTime != null'>AND rp.start_time &gt;= #{query.startTime}</if>" +
            "   <if test='query.endTime != null'>AND rp.end_time &lt;= #{query.endTime}</if>" +
            "</where>" +
            "ORDER BY rp.sort DESC, rp.create_time DESC" +
            "</script>")
    List<AdminRecommendDTO> selectAdminRecommendList(Page<AdminRecommendDTO> page, 
                                                   @Param("query") RecommendPageQueryDTO query);

    /**
     * 获取推荐商品详情
     */
    @Select("SELECT rp.id AS recommend_id, p.name AS product_name, " +
            "JSON_UNQUOTE(JSON_EXTRACT(p.images, '$[0]')) AS main_image, " +
            "p.price, rp.type, " +
            "(CASE rp.type WHEN 1 THEN '热门商品' WHEN 2 THEN '新品推荐' END) AS type_desc, " +
            "rp.sort, (CASE rp.status WHEN 0 THEN '未生效' WHEN 1 THEN '生效中' END) AS status_desc, " +
            "rp.start_time, rp.end_time, rp.algorithm_version, rp.weight, rp.recommend_reason " +
            "FROM recommend_product rp " +
            "JOIN products p ON rp.product_id = p.id " +
            "WHERE rp.id = #{recommendId}")
    RecommendDetailDTO selectRecommendDetail(@Param("recommendId") Long recommendId);

    /**
     * 更新推荐状态
     */
    @Update("UPDATE recommend_product SET status = #{status} WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") Integer status);

    /**
     * 检查推荐记录是否存在
     */
    @Select("SELECT COUNT(*) FROM recommend_product WHERE product_id = #{productId} AND type = #{type}")
    int existsRecommend(@Param("productId") Long productId, @Param("type") Integer type);
}





