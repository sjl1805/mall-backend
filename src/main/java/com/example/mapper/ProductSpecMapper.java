package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.model.dto.spec.AdminSpecDTO;
import com.example.model.dto.spec.SpecDetailDTO;
import com.example.model.dto.spec.SpecPageQueryDTO;
import com.example.model.entity.ProductSpec;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

/**
 * @author 31815
 * @description 针对表【product_spec(商品规格表)】的数据库操作Mapper
 * @createDate 2025-02-10 02:08:54
 * @Entity gg.model.ProductSpec
 */
@Mapper
public interface ProductSpecMapper extends BaseMapper<ProductSpec> {

    /**
     * 分页查询规格（管理员用）
     */
    @Results(id = "adminSpecMap", value = {
            @Result(property = "productId", column = "product_id"),
            @Result(property = "productName", column = "product_name"),
            @Result(property = "specName", column = "spec_name"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "updateTime", column = "update_time")
    })
    @Select("<script>" +
            "SELECT ps.id, ps.product_id AS productId, p.name AS productName, " +
            "ps.spec_name AS specName, ps.spec_values AS specValues, " +
            "ps.create_time, ps.update_time " +
            "FROM product_spec ps " +
            "JOIN products p ON ps.product_id = p.id " +
            "<where>" +
            "   <if test='query.productId != null'>AND ps.product_id = #{query.productId}</if>" +
            "   <if test='query.specName != null'>AND ps.spec_name LIKE CONCAT('%',#{query.specName},'%')</if>" +
            "</where>" +
            "ORDER BY ps.create_time DESC" +
            "</script>")
    List<AdminSpecDTO> selectAdminSpecList(Page<AdminSpecDTO> page,
                                           @Param("query") SpecPageQueryDTO query);

    /**
     * 获取规格详情
     */
    @Select("SELECT ps.id, p.name AS productName, ps.spec_name AS specName, ps.spec_values AS specValues " +
            "FROM product_spec ps " +
            "JOIN products p ON ps.product_id = p.id " +
            "WHERE ps.id = #{specId}")
    @Options(useCache = true)
    Optional<SpecDetailDTO> selectSpecDetail(@Param("specId") Long specId);


    /**
     * 批量删除规格
     */
    @Update("<script>" +
            "DELETE FROM product_spec " +
            "WHERE id IN " +
            "<foreach collection='specIds' item='id' open='(' separator=',' close=')'>" +
            "#{id}" +
            "</foreach>" +
            " AND product_id IN (SELECT product_id FROM products WHERE merchant_id = #{merchantId})"
            + "</script>")
    int batchDeleteSpecs(@Param("specIds") List<Long> specIds,
                         @Param("merchantId") Long merchantId);

    /**
     * 检查规格是否被SKU使用
     */
    @Select("SELECT COUNT(*) FROM product_sku WHERE spec_values LIKE CONCAT('%',#{specValue},'%')")
    @Options(useCache = true, flushCache = Options.FlushCachePolicy.FALSE)
    int countSkuUsage(@Param("specValue") String specValue);

    /**
     * 根据商品ID统计规格数量
     */
    @Select("SELECT COUNT(*) FROM product_spec WHERE product_id = #{productId}")
    int countByProduct(@Param("productId") Long productId);
}





