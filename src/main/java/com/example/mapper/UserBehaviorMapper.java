package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.model.dto.behavior.AdminBehaviorDTO;
import com.example.model.dto.behavior.BehaviorStatsDTO;
import com.example.model.dto.behavior.BehaviorPageQueryDTO;

import com.example.model.dto.behavior.BehaviorAnalysisQueryDTO;
import com.example.model.entity.UserBehavior;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


import java.util.List;
import java.time.LocalDateTime;
/**
 * @author 31815
 * @description 针对表【user_behavior(用户行为记录表)】的数据库操作Mapper
 * @createDate 2025-02-10 02:08:35
 * @Entity gg.model.UserBehavior
 */
@Mapper
public interface UserBehaviorMapper extends BaseMapper<UserBehavior> {

    /**
     * 分页查询用户行为记录（管理员用）
     */
    @Select("<script>"
            + "SELECT ub.*, u.username, p.name as product_name "
            + "FROM user_behavior ub "
            + "LEFT JOIN users u ON ub.user_id = u.id "
            + "LEFT JOIN product p ON ub.product_id = p.id "
            + "<where>"
            + "   <if test='query.userId != null'>AND ub.user_id = #{query.userId}</if>"
            + "   <if test='query.productId != null'>AND ub.product_id = #{query.productId}</if>"
            + "   <if test='query.behaviorType != null'>AND ub.behavior_type = #{query.behaviorType}</if>"
            + "   <if test='query.startTime != null'>AND ub.behavior_time &gt;= #{query.startTime}</if>"
            + "   <if test='query.endTime != null'>AND ub.behavior_time &lt;= #{query.endTime}</if>"
            + "</where>"
            + "ORDER BY ub.behavior_time DESC"
            + "</script>")
    List<AdminBehaviorDTO> selectAdminBehaviorList(Page<AdminBehaviorDTO> page, @Param("query") BehaviorPageQueryDTO query);

    /**
     * 用户行为统计分析（支持多维度）
     */
    @Select("<script>" +
            "SELECT ${timeFunction} AS time_segment, " +
            "SUM(CASE WHEN behavior_type = 'VIEW' THEN 1 ELSE 0 END) AS view_count, " +
            "SUM(CASE WHEN behavior_type = 'COLLECT' THEN 1 ELSE 0 END) AS collect_count, " +
            "SUM(CASE WHEN behavior_type = 'PURCHASE' THEN 1 ELSE 0 END) AS purchase_count, " +
            "ROUND(AVG(duration), 2) AS avg_duration " +
            "FROM user_behavior " +
            "WHERE 1=1 " +
            "<if test='query.productId != null'>AND product_id = #{query.productId}</if>" +
            "<if test='query.behaviorType != null'>AND behavior_type = #{query.behaviorType}</if>" +
            "<if test='query.startTime != null'>AND behavior_time &gt;= #{query.startTime}</if>" +
            "<if test='query.endTime != null'>AND behavior_time &lt;= #{query.endTime}</if>" +
            "GROUP BY time_segment " +
            "ORDER BY time_segment ASC" +
            "</script>")
    List<BehaviorStatsDTO> analyzeBehavior(@Param("query") BehaviorAnalysisQueryDTO query,
                                          @Param("timeFunction") String timeFunction);

    /**
     * 获取用户最近浏览记录（带商品图片）
     */
    @Select("SELECT ub.id, ub.product_id, ub.behavior_time, " +
            "p.name as product_name, p.image as product_image " +
            "FROM user_behavior ub " +
            "LEFT JOIN product p ON ub.product_id = p.id " +
            "WHERE ub.user_id = #{userId} AND ub.behavior_type = 'VIEW' " +
            "ORDER BY ub.behavior_time DESC " +
            "LIMIT #{limit}")
    List<AdminBehaviorDTO> selectRecentViews(@Param("userId") Long userId, @Param("limit") int limit);

    /**
     * 统计商品行为数据（带缓存标记）
     */
    @Select("SELECT " +
            "COUNT(CASE WHEN behavior_type = 'VIEW' THEN 1 END) AS view_count, " +
            "COUNT(CASE WHEN behavior_type = 'COLLECT' THEN 1 END) AS collect_count, " +
            "COUNT(CASE WHEN behavior_type = 'PURCHASE' THEN 1 END) AS purchase_count, " +
            "ROUND(AVG(duration), 2) AS avg_duration " +
            "FROM user_behavior " +
            "WHERE product_id = #{productId} " +
            "AND behavior_time &gt;= DATE_SUB(NOW(), INTERVAL 30 DAY)")
    BehaviorStatsDTO selectProductBehaviorStatsWithCache(@Param("productId") Long productId);

    /**
     * 获取用户行为热力图数据（按小时统计）
     */
    @Select("SELECT HOUR(behavior_time) as hour, COUNT(*) as count " +
            "FROM user_behavior " +
            "WHERE user_id = #{userId} " +
            "AND behavior_time BETWEEN #{startTime} AND #{endTime} " +
            "GROUP BY HOUR(behavior_time) " +
            "ORDER BY hour ASC")
    List<BehaviorStatsDTO> selectUserActivityHeatmap(@Param("userId") Long userId,
                                                   @Param("startTime") LocalDateTime startTime,
                                                   @Param("endTime") LocalDateTime endTime);

    /**
     * 批量插入用户行为记录（高性能）
     */
    int batchInsertBehaviors(@Param("list") List<UserBehavior> behaviors);
}





