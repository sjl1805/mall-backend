package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.model.entity.ProductReview;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 31815
 * @description 针对表【product_review(商品评价表)】的数据库操作Mapper
 * @createDate 2025-02-10 02:09:03
 * @Entity gg.model.ProductReview
 */
@Mapper
public interface ProductReviewMapper extends BaseMapper<ProductReview> {

}





