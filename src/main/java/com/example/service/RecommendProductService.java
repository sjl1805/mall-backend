package com.example.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.model.dto.recommend.RecommendDetailDTO;
import com.example.model.dto.recommend.RecommendPageQueryDTO;
import com.example.model.entity.RecommendProduct;


import java.util.Optional;

/**
 * @author 31815
 * @description 针对表【recommend_product(推荐商品表)】的数据库操作Service
 * @createDate 2025-02-10 02:08:45
 */
public interface RecommendProductService extends IService<RecommendProduct> {
    IPage<RecommendPageQueryDTO> getRecommendPage(RecommendPageQueryDTO query);


    Optional<RecommendDetailDTO> getRecommendDetail(Long recommendId);

    boolean updateRecommendStatus(Long id, Integer status);

    boolean checkRecommendExists(Long productId, Integer type);
}

