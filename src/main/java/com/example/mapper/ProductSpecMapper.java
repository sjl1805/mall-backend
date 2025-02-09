package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.model.entity.ProductSpec;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 31815
 * @description 针对表【product_spec(商品规格表)】的数据库操作Mapper
 * @createDate 2025-02-10 02:08:54
 * @Entity gg.model.ProductSpec
 */
@Mapper
public interface ProductSpecMapper extends BaseMapper<ProductSpec> {

}





