package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.model.entity.ProductFavorite;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 31815
 * @description 针对表【product_favorite(商品收藏表)】的数据库操作Mapper
 * @createDate 2025-02-10 02:09:07
 * @Entity gg.model.ProductFavorite
 */
@Mapper
public interface ProductFavoriteMapper extends BaseMapper<ProductFavorite> {

}





