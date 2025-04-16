package com.example.config;

import com.example.service.ProductSimilarityService;
import com.example.service.RecommendationService;
import com.example.service.UserPreferenceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 定时任务配置
 * 配置系统中需要定期执行的任务
 */
@Configuration
@EnableScheduling
@RequiredArgsConstructor
@Slf4j
public class SchedulingConfig {

    private final ProductSimilarityService productSimilarityService;
    private final UserPreferenceService userPreferenceService;
    private final RecommendationService recommendationService;

    /**
     * 每天凌晨2点计算所有商品之间的相似度
     * 这是一个耗时操作，应该在系统负载较低的时候执行
     */
    @Scheduled(cron = "0 0 2 * * ?")
    public void calculateProductSimilarities() {
        log.info("开始执行定时任务: 计算商品相似度");

        try {
            int count = productSimilarityService.calculateAndUpdateAllProductSimilarities();
            log.info("商品相似度计算完成，共更新 {} 条记录", count);
        } catch (Exception e) {
            log.error("商品相似度计算失败", e);
        }
    }

    /**
     * 每周一凌晨3点重新计算所有用户偏好
     * 可以根据实际需求调整频率
     */
    @Scheduled(cron = "0 0 3 ? * MON")
    public void updateUserPreferences() {
        log.info("开始执行定时任务: 更新用户偏好");

        try {
            int count = userPreferenceService.calculateAndUpdateAllUserPreferences();
            log.info("用户偏好更新完成，共更新 {} 条记录", count);
        } catch (Exception e) {
            log.error("用户偏好更新失败", e);
        }
    }

    /**
     * 每天凌晨4点更新所有用户的推荐结果
     * 在计算完商品相似度和用户偏好之后执行
     */
    @Scheduled(cron = "0 0 4 * * ?")
    public void updateAllRecommendations() {
        log.info("开始执行定时任务: 更新所有用户推荐结果");

        try {
            // 每种推荐类型生成10个推荐
            int count = recommendationService.updateAllUsersRecommendations(10);
            log.info("所有用户推荐结果更新完成，共更新 {} 条记录", count);
        } catch (Exception e) {
            log.error("更新所有用户推荐结果失败", e);
        }
    }
} 