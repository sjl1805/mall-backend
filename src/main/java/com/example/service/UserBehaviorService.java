package com.example.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.model.dto.behavior.AdminBehaviorDTO;
import com.example.model.dto.behavior.BehaviorAnalysisQueryDTO;
import com.example.model.dto.behavior.BehaviorPageQueryDTO;
import com.example.model.dto.behavior.BehaviorStatsDTO;
import com.example.model.entity.UserBehavior;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 31815
 * @description 针对表【user_behavior(用户行为记录表)】的数据库操作Service
 * @createDate 2025-02-10 02:08:35
 */
public interface UserBehaviorService extends IService<UserBehavior> {
    BehaviorPageQueryDTO getBehaviorPage(BehaviorPageQueryDTO query);

    List<BehaviorStatsDTO> analyzeBehavior(BehaviorAnalysisQueryDTO query, String timeFunction);

    List<AdminBehaviorDTO> getRecentViews(Long userId, int limit);

    BehaviorStatsDTO getProductBehaviorStats(Long productId);

    List<BehaviorStatsDTO> getUserActivityHeatmap(Long userId, LocalDateTime startTime, LocalDateTime endTime);

    boolean batchInsertBehaviors(List<UserBehavior> behaviors);
}
