package com.example.mapper;

import com.baomidou.mybatisplus.core.handlers.MybatisEnumTypeHandler;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.model.dto.recommend.RecommendDetailDTO;
import com.example.model.dto.recommend.RecommendPageQueryDTO;
import com.example.model.entity.RecommendProduct;
import org.apache.ibatis.annotations.*;

import java.util.Optional;

/**
 * @author 31815
 * @description 针对表【recommend_product(推荐商品表)】的数据库操作Mapper
 * @createDate 2025-02-10 02:08:45
 * @Entity gg.model.RecommendProduct
 */
public interface RecommendProductMapper extends BaseMapper<RecommendProduct> {

    /**
     * 分页查询推荐商品（管理端）
     */
    @Results(id = "adminRecommendMap", value = {
            @Result(property = "productName", column = "product_name"),
            @Result(property = "typeDesc", column = "type", typeHandler = MybatisEnumTypeHandler.class),
            @Result(property = "statusDesc", column = "status", typeHandler = MybatisEnumTypeHandler.class),
            @Result(property = "startTime", column = "start_time"),
            @Result(property = "endTime", column = "end_time")
    })
    @Select("<script>"
            + "SELECT rp.*, p.name AS product_name "
            + "FROM recommend_product rp "
            + "JOIN products p ON rp.product_id = p.id "
            + "<where>"
            + "   <if test='query.type != null'>AND rp.type = #{query.type}</if>"
            + "   <if test='query.status != null'>AND rp.status = #{query.status}</if>"
            + "</where>"
            + "</script>")
    IPage<RecommendPageQueryDTO> selectAdminRecommendList(@Param("query") RecommendPageQueryDTO query);



    /**
     * 获取推荐商品详情
     */
    @Results(id = "recommendDetailMap", value = {
            @Result(property = "recommendId", column = "recommend_id"),
            @Result(property = "mainImage", column = "main_image"),
            @Result(property = "productName", column = "product_name"),
            @Result(property = "price", column = "price"),
            @Result(property = "type", column = "type"),
            @Result(property = "typeDesc", column = "type_desc"),
            @Result(property = "sort", column = "sort"),
            @Result(property = "statusDesc", column = "status_desc"),
            @Result(property = "startTime", column = "start_time"),
            @Result(property = "endTime", column = "end_time"),
            @Result(property = "algorithmVersion", column = "algorithm_version"),
            @Result(property = "weight", column = "weight"),
            @Result(property = "recommendReason", column = "recommend_reason")
    })
    @Select("SELECT rp.id AS recommend_id, p.name AS product_name, " +
            "JSON_UNQUOTE(JSON_EXTRACT(p.images, '$[0]')) AS main_image, " +
            "p.price, rp.type, " +
            "(CASE rp.type WHEN 1 THEN '热门商品' WHEN 2 THEN '新品推荐' END) AS type_desc, " +
            "rp.sort, (CASE rp.status WHEN 0 THEN '未生效' WHEN 1 THEN '生效中' END) AS status_desc, " +
            "rp.start_time, rp.end_time, rp.algorithm_version, rp.weight, rp.recommend_reason " +
            "FROM recommend_product rp " +
            "JOIN products p ON rp.product_id = p.id " +
            "WHERE rp.id = #{recommendId}")
    @Options(useCache = true, flushCache = Options.FlushCachePolicy.FALSE)
    Optional<RecommendDetailDTO> selectRecommendDetail(@Param("recommendId") Long recommendId);


    /**
     * 更新推荐状态
     */
    @Update("UPDATE recommend_product SET status = #{status} WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") Integer status);

    /**
     * 检查推荐记录是否存在
     */
    @Select("SELECT COUNT(*) FROM recommend_product WHERE product_id = #{productId} AND type = #{type}")
    int existsRecommend(@Param("productId") Long productId,
                        @Param("type") Integer type);
}





