package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mapper.RecommendProductMapper;
import com.example.model.entity.RecommendProduct;
import com.example.service.RecommendProductService;
import com.example.exception.PermissionDeniedException;
import com.example.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 31815
 * @description 针对表【recommend_product(推荐商品表)】的数据库操作Service实现
 * @createDate 2025-02-10 02:08:45
 */
@Service
public class RecommendProductServiceImpl extends ServiceImpl<RecommendProductMapper, RecommendProduct>
        implements RecommendProductService {

    private boolean hasPermission() {
        return SecurityUtils.getCurrentUser().getRole() == UserRoleEnum.ADMIN;
    }

    public void updateStatus(Long id, Integer status) {
        // 校验操作权限
        if (!hasPermission()) {
            throw new PermissionDeniedException("无操作权限");
        }
        // 添加事务注解
        @Transactional
        baseMapper.updateStatus(id, status);
    }
}




