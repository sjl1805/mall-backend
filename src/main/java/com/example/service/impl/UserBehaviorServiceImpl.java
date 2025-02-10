package com.example.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mapper.UserBehaviorMapper;
import com.example.model.dto.behavior.AdminBehaviorDTO;
import com.example.model.dto.behavior.BehaviorAnalysisQueryDTO;
import com.example.model.dto.behavior.BehaviorPageQueryDTO;
import com.example.model.dto.behavior.BehaviorStatsDTO;
import com.example.model.entity.UserBehavior;
import com.example.service.UserBehaviorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 31815
 * @description 针对表【user_behavior(用户行为记录表)】的数据库操作Service实现
 * @createDate 2025-02-10 02:08:35
 */
@Service
@RequiredArgsConstructor
public class UserBehaviorServiceImpl extends ServiceImpl<UserBehaviorMapper, UserBehavior>
        implements UserBehaviorService {

    private final UserBehaviorMapper userBehaviorMapper;

    @Override
    public IPage<BehaviorPageQueryDTO> getBehaviorPage(BehaviorPageQueryDTO query) {
        return userBehaviorMapper.selectAdminBehaviorList(query);
    }


    @Override
    public List<BehaviorStatsDTO> analyzeBehavior(BehaviorAnalysisQueryDTO query, String timeFunction) {
        return userBehaviorMapper.analyzeBehavior(query, timeFunction);
    }

    @Override
    public List<AdminBehaviorDTO> getRecentViews(Long userId, int limit) {
        return userBehaviorMapper.selectRecentViews(userId, Math.min(limit, 100));
    }

    @Override
    public BehaviorStatsDTO getProductBehaviorStats(Long productId) {
        return userBehaviorMapper.selectProductBehaviorStatsWithCache(productId)
                .orElse(new BehaviorStatsDTO());
    }

    @Override
    public List<BehaviorStatsDTO> getUserActivityHeatmap(Long userId, LocalDateTime startTime, LocalDateTime endTime) {
        return userBehaviorMapper.selectUserActivityHeatmap(userId, startTime, endTime);
    }

    @Override
    @Transactional
    public boolean batchInsertBehaviors(List<UserBehavior> behaviors) {
        return userBehaviorMapper.batchInsertBehaviors(behaviors) > 0;
    }
}




