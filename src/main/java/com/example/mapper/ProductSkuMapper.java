package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.model.dto.sku.SkuDetailDTO;
import com.example.model.dto.sku.SkuPageQueryDTO;
import com.example.model.entity.ProductSku;
import com.example.model.enums.ProductSkuStatusEnum;
import org.apache.ibatis.annotations.*;


import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * @author 31815
 * @description 针对表【product_sku(商品SKU表)】的数据库操作Mapper
 * @createDate 2025-02-10 02:08:59
 * @Entity gg.model.ProductSku
 */
public interface ProductSkuMapper extends BaseMapper<ProductSku> {

    /**
     * 分页查询SKU（管理员用）
     */
    @Results(id = "adminSkuMap", value = {
            @Result(property = "productId", column = "product_id"),
            @Result(property = "productName", column = "product_name"),
            @Result(property = "mainImage", column = "main_image"),
            @Result(property = "statusDesc", column = "status_desc"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "updateTime", column = "update_time")
    })
    @Select("<script>" +
            "SELECT sku.id, sku.product_id AS productId, p.name AS productName, " +
            "sku.spec_values, sku.price, sku.stock, sku.sales, " +
            "sku.main_image AS mainImage, sku.status AS statusDesc, " +
            "sku.create_time, sku.update_time " +
            "FROM product_sku sku " +
            "JOIN products p ON sku.product_id = p.id " +
            "<where>" +
            "   <if test='query.productId != null'>AND sku.product_id = #{query.productId}</if>" +
            "   <if test='query.specKeyword != null'>AND sku.spec_values LIKE CONCAT('%',#{query.specKeyword},'%')</if>" +
            "   <if test='query.minPrice != null'>AND sku.price &gt;= #{query.minPrice}</if>" +
            "   <if test='query.maxPrice != null'>AND sku.price &lt;= #{query.maxPrice}</if>" +
            "   <if test='query.status != null'>AND sku.status = #{query.status}</if>" +
            "</where>" +
            "ORDER BY sku.create_time DESC" +
            "</script>")
    IPage<SkuPageQueryDTO> selectAdminSkuList(@Param("query") SkuPageQueryDTO query);



    /**
     * 更新SKU库存（带乐观锁）
     */
    @Update("UPDATE product_sku SET stock = stock - #{quantity}, version = version + 1 " +
            "WHERE id = #{skuId} AND stock >= #{quantity} AND version = #{version}")
    int deductStock(@Param("skuId") Long skuId,
                    @Param("quantity") Integer quantity,
                    @Param("version") Integer version);

    /**
     * 获取SKU详情
     */
    @Options(useCache = true, flushCache = Options.FlushCachePolicy.FALSE)
    @Select("SELECT sku.id AS skuId, p.name AS productName, sku.spec_values, " +
            "sku.price, sku.stock, sku.sales, sku.main_image, sku.status AS statusDesc " +
            "FROM product_sku sku " +
            "JOIN products p ON sku.product_id = p.id " +
            "WHERE sku.id = #{skuId}")
    @Results(id = "skuDetailMap", value = {
            @Result(property = "skuId", column = "sku_id")
    })
    Optional<SkuDetailDTO> selectSkuDetail(@Param("skuId") Long skuId);


    /**
     * 批量更新SKU状态
     */
    @Update("<script>" +
            "UPDATE product_sku SET status = #{status} " +
            "WHERE id IN " +
            "<foreach collection='skuIds' item='id' open='(' separator=',' close=')'>" +
            "#{id}" +
            "</foreach>" +
            " AND product_id IN (SELECT product_id FROM products WHERE merchant_id = #{merchantId})"
            + "</script>")
    int batchUpdateStatus(@Param("skuIds") List<Long> skuIds,
                          @Param("status") ProductSkuStatusEnum status,
                          @Param("merchantId") Long merchantId);

    /**
     * 根据商品ID获取最低价SKU
     */
    @Select("SELECT MIN(price) FROM product_sku WHERE product_id = #{productId} AND status = 'ON_SALE'")
    BigDecimal selectMinPriceByProduct(@Param("productId") Long productId);

    /**
     * 更新SKU销量
     */
    @Update("UPDATE product_sku SET sales = sales + #{quantity} WHERE id = #{skuId}")
    int increaseSales(@Param("skuId") Long skuId,
                      @Param("quantity") Integer quantity);
}





