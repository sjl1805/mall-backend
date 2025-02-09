package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.model.entity.Cart;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 31815
 * @description 针对表【cart(购物车表)】的数据库操作Mapper
 * @createDate 2025-02-10 02:09:28
 * @Entity gg.model.Cart
 */
@Mapper
public interface CartMapper extends BaseMapper<Cart> {

}




