package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.model.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 31815
 * @description 针对表【orders(订单表)】的数据库操作Mapper
 * @createDate 2025-02-10 02:09:11
 * @Entity gg.model.Orders
 */
@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {

}





