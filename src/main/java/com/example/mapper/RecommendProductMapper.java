package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.model.entity.RecommendProduct;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 31815
 * @description 针对表【recommend_product(推荐商品表)】的数据库操作Mapper
 * @createDate 2025-02-10 02:08:45
 * @Entity gg.model.RecommendProduct
 */ 
@Mapper
public interface RecommendProductMapper extends BaseMapper<RecommendProduct> {

}





