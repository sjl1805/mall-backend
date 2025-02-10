package com.example.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mapper.RecommendProductMapper;
import com.example.model.dto.recommend.RecommendDetailDTO;
import com.example.model.dto.recommend.RecommendPageQueryDTO;
import com.example.model.entity.RecommendProduct;
import com.example.service.RecommendProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author 31815
 * @description 针对表【recommend_product(推荐商品表)】的数据库操作Service实现
 * @createDate 2025-02-10 02:08:45
 */
@Service
@RequiredArgsConstructor
public class RecommendProductServiceImpl extends ServiceImpl<RecommendProductMapper, RecommendProduct>
        implements RecommendProductService {

    private final RecommendProductMapper recommendProductMapper;

    @Override
    public IPage<RecommendPageQueryDTO> getRecommendPage(RecommendPageQueryDTO query) {
        return recommendProductMapper.selectAdminRecommendList(query);
    }



    @Override
    public Optional<RecommendDetailDTO> getRecommendDetail(Long recommendId) {
        return recommendProductMapper.selectRecommendDetail(recommendId);
    }

    @Override
    @Transactional
    public boolean updateRecommendStatus(Long id, Integer status) {
        return recommendProductMapper.updateStatus(id, status) > 0;
    }

    @Override
    public boolean checkRecommendExists(Long productId, Integer type) {
        return recommendProductMapper.existsRecommend(productId, type) > 0;
    }
}




