package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.model.entity.Products;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 31815
 * @description 针对表【products(商品表)】的数据库操作Mapper
 * @createDate 2025-02-10 02:08:50
 * @Entity gg.model.Products
 */
@Mapper
public interface ProductsMapper extends BaseMapper<Products> {

}





