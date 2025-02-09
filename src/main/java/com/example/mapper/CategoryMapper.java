package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.model.entity.Category;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 31815
 * @description 针对表【category(商品分类表)】的数据库操作Mapper
 * @createDate 2025-02-10 02:09:24
 * @Entity gg.model.Category
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

}




