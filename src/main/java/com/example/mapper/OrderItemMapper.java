package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.model.entity.OrderItem;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 31815
 * @description 针对表【order_item(订单商品表)】的数据库操作Mapper
 * @createDate 2025-02-10 02:09:15
 * @Entity gg.model.OrderItem
 */
@Mapper
public interface OrderItemMapper extends BaseMapper<OrderItem> {

}





