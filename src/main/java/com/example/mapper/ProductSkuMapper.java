package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.model.entity.ProductSku;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 31815
 * @description 针对表【product_sku(商品SKU表)】的数据库操作Mapper
 * @createDate 2025-02-10 02:08:59
 * @Entity gg.model.ProductSku
 */
@Mapper
public interface ProductSkuMapper extends BaseMapper<ProductSku> {

}





