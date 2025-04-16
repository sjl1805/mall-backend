package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.ResultCode;
import com.example.exception.BusinessException;
import com.example.mapper.UserBehaviorMapper;
import com.example.model.entity.Product;
import com.example.model.entity.UserBehavior;
import com.example.model.vo.UserBehaviorVO;
import com.example.service.ProductService;
import com.example.service.UserBehaviorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 28619
 * @description 针对表【user_behavior(用户行为记录表)】的数据库操作Service实现
 * @createDate 2025-04-09 17:42:46
 */
@Service
@RequiredArgsConstructor
public class UserBehaviorServiceImpl extends ServiceImpl<UserBehaviorMapper, UserBehavior>
        implements UserBehaviorService {

    // 行为类型常量
    private static final int BEHAVIOR_VIEW = 1;    // 浏览
    private static final int BEHAVIOR_FAVORITE = 2; // 收藏
    private static final int BEHAVIOR_CART = 3;     // 加购
    private static final int BEHAVIOR_PURCHASE = 4; // 购买
    private static final int BEHAVIOR_REVIEW = 5;   // 评价
    private final ProductService productService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean recordBehavior(Long userId, Long productId, Integer behaviorType) {
        if (userId == null || productId == null || behaviorType == null) {
            throw new BusinessException("参数错误", ResultCode.PARAM_ERROR);
        }

        // 验证商品是否存在
        Product product = productService.getById(productId);
        if (product == null) {
            throw new BusinessException("商品不存在", ResultCode.PARAM_ERROR);
        }

        // 验证行为类型是否合法
        if (behaviorType < BEHAVIOR_VIEW || behaviorType > BEHAVIOR_REVIEW) {
            throw new BusinessException("行为类型不合法", ResultCode.PARAM_ERROR);
        }

        // 创建用户行为记录
        UserBehavior userBehavior = new UserBehavior();
        userBehavior.setUserId(userId);
        userBehavior.setProductId(productId);
        userBehavior.setBehaviorType(behaviorType);
        userBehavior.setBehaviorTime(new Date()); // 设置行为时间为当前时间

        return save(userBehavior);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean recordBehaviorBatch(Long userId, List<Long> productIds, Integer behaviorType) {
        if (userId == null || productIds == null || productIds.isEmpty() || behaviorType == null) {
            throw new BusinessException("参数错误", ResultCode.PARAM_ERROR);
        }

        // 验证行为类型是否合法
        if (behaviorType < BEHAVIOR_VIEW || behaviorType > BEHAVIOR_REVIEW) {
            throw new BusinessException("行为类型不合法", ResultCode.PARAM_ERROR);
        }

        // 验证商品是否都存在
        List<Product> products = productService.listByIds(productIds);
        if (products.size() != productIds.size()) {
            throw new BusinessException("部分商品不存在", ResultCode.PARAM_ERROR);
        }

        // 批量创建用户行为记录
        List<UserBehavior> userBehaviors = new ArrayList<>();
        Date now = new Date();

        for (Long productId : productIds) {
            UserBehavior userBehavior = new UserBehavior();
            userBehavior.setUserId(userId);
            userBehavior.setProductId(productId);
            userBehavior.setBehaviorType(behaviorType);
            userBehavior.setBehaviorTime(now);
            userBehaviors.add(userBehavior);
        }

        return saveBatch(userBehaviors);
    }

    @Override
    public List<UserBehavior> getUserBehaviorHistory(Long userId, Integer behaviorType,
                                                     Date startTime, Date endTime, Integer limit) {
        if (userId == null) {
            throw new BusinessException("用户ID不能为空", ResultCode.PARAM_ERROR);
        }

        // 如果没有指定数量限制，默认为100
        if (limit == null || limit <= 0) {
            limit = 100;
        }

        LambdaQueryWrapper<UserBehavior> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserBehavior::getUserId, userId);

        // 根据行为类型筛选
        if (behaviorType != null) {
            queryWrapper.eq(UserBehavior::getBehaviorType, behaviorType);
        }

        // 根据时间范围筛选
        if (startTime != null) {
            queryWrapper.ge(UserBehavior::getBehaviorTime, startTime);
        }
        if (endTime != null) {
            queryWrapper.le(UserBehavior::getBehaviorTime, endTime);
        }

        // 按行为时间降序排序，并限制数量
        queryWrapper.orderByDesc(UserBehavior::getBehaviorTime);
        queryWrapper.last("LIMIT " + limit);

        return list(queryWrapper);
    }

    @Override
    public Page<UserBehaviorVO> getUserBehaviorHistoryVO(Long userId, Integer behaviorType,
                                                         Date startTime, Date endTime, long page, long size) {
        if (userId == null) {
            throw new BusinessException("用户ID不能为空", ResultCode.PARAM_ERROR);
        }

        if (page <= 0) {
            page = 1;
        }
        if (size <= 0) {
            size = 10;
        }

        // 构建查询条件
        LambdaQueryWrapper<UserBehavior> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserBehavior::getUserId, userId);

        // 根据行为类型筛选
        if (behaviorType != null) {
            queryWrapper.eq(UserBehavior::getBehaviorType, behaviorType);
        }

        // 根据时间范围筛选
        if (startTime != null) {
            queryWrapper.ge(UserBehavior::getBehaviorTime, startTime);
        }
        if (endTime != null) {
            queryWrapper.le(UserBehavior::getBehaviorTime, endTime);
        }

        // 按行为时间降序排序
        queryWrapper.orderByDesc(UserBehavior::getBehaviorTime);

        // 查询分页数据
        Page<UserBehavior> behaviorPage = page(new Page<>(page, size), queryWrapper);

        // 转换为VO对象
        Page<UserBehaviorVO> voPage = new Page<>(behaviorPage.getCurrent(), behaviorPage.getSize(), behaviorPage.getTotal());

        if (behaviorPage.getRecords().isEmpty()) {
            voPage.setRecords(new ArrayList<>());
            return voPage;
        }

        // 获取关联的商品信息
        Set<Long> productIds = behaviorPage.getRecords().stream()
                .map(UserBehavior::getProductId)
                .collect(Collectors.toSet());

        Map<Long, Product> productMap = new HashMap<>();
        if (!productIds.isEmpty()) {
            List<Product> products = productService.listByIds(productIds);
            productMap = products.stream()
                    .collect(Collectors.toMap(Product::getId, product -> product));
        }

        // 转换为VO对象
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final Map<Long, Product> finalProductMap = productMap;

        List<UserBehaviorVO> voList = behaviorPage.getRecords().stream().map(behavior -> {
            UserBehaviorVO vo = new UserBehaviorVO();
            BeanUtils.copyProperties(behavior, vo);

            // 设置行为类型描述
            vo.setBehaviorTypeDesc(getBehaviorTypeDesc(behavior.getBehaviorType()));

            // 设置商品信息
            Product product = finalProductMap.get(behavior.getProductId());
            if (product != null) {
                vo.setProductName(product.getName());
                vo.setProductImage(product.getImage());
                vo.setProductPrice(product.getPrice());
            } else {
                vo.setProductName("未知商品");
                vo.setProductImage("");
            }

            // 格式化时间
            if (behavior.getBehaviorTime() != null) {
                vo.setBehaviorTimeStr(sdf.format(behavior.getBehaviorTime()));
            }

            return vo;
        }).collect(Collectors.toList());

        voPage.setRecords(voList);
        return voPage;
    }

    @Override
    public List<Product> getRecentViewedProducts(Long userId, int limit) {
        if (userId == null) {
            throw new BusinessException("用户ID不能为空", ResultCode.PARAM_ERROR);
        }

        if (limit <= 0) {
            limit = 10; // 默认获取10条
        }

        // 获取最近浏览记录
        LambdaQueryWrapper<UserBehavior> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserBehavior::getUserId, userId);
        queryWrapper.eq(UserBehavior::getBehaviorType, BEHAVIOR_VIEW); // 浏览行为
        queryWrapper.orderByDesc(UserBehavior::getBehaviorTime);
        queryWrapper.last("LIMIT " + limit);

        List<UserBehavior> behaviors = list(queryWrapper);

        if (behaviors.isEmpty()) {
            return new ArrayList<>();
        }

        // 获取商品ID列表，去重
        List<Long> productIds = behaviors.stream()
                .map(UserBehavior::getProductId)
                .distinct()
                .collect(Collectors.toList());

        // 查询商品详情
        List<Product> products = productService.listByIds(productIds);

        // 按照浏览时间重新排序
        Map<Long, Product> productMap = products.stream()
                .collect(Collectors.toMap(Product::getId, product -> product));

        return behaviors.stream()
                .map(UserBehavior::getProductId)
                .distinct()
                .filter(productMap::containsKey)
                .map(productMap::get)
                .collect(Collectors.toList());
    }

    @Override
    public Map<Integer, Long> getUserBehaviorStats(Long userId) {
        if (userId == null) {
            throw new BusinessException("用户ID不能为空", ResultCode.PARAM_ERROR);
        }

        LambdaQueryWrapper<UserBehavior> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserBehavior::getUserId, userId);

        List<UserBehavior> behaviors = list(queryWrapper);

        // 统计各类行为的数量
        Map<Integer, Long> stats = behaviors.stream()
                .collect(Collectors.groupingBy(UserBehavior::getBehaviorType, Collectors.counting()));

        // 确保所有行为类型都有统计数据
        for (int i = BEHAVIOR_VIEW; i <= BEHAVIOR_REVIEW; i++) {
            stats.putIfAbsent(i, 0L);
        }

        return stats;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean clearBehaviorRecords(Long userId, Integer behaviorType, Date beforeTime) {
        if (userId == null) {
            throw new BusinessException("用户ID不能为空", ResultCode.PARAM_ERROR);
        }

        LambdaUpdateWrapper<UserBehavior> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(UserBehavior::getUserId, userId);

        // 根据行为类型筛选
        if (behaviorType != null) {
            updateWrapper.eq(UserBehavior::getBehaviorType, behaviorType);
        }

        // 根据时间筛选
        if (beforeTime != null) {
            updateWrapper.lt(UserBehavior::getBehaviorTime, beforeTime);
        }

        return remove(updateWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean cancelBehavior(Long userId, Long productId, Integer behaviorType) {
        if (userId == null || productId == null || behaviorType == null) {
            throw new BusinessException("参数错误", ResultCode.PARAM_ERROR);
        }

        // 验证行为类型是否合法
        if (behaviorType < BEHAVIOR_VIEW || behaviorType > BEHAVIOR_REVIEW) {
            throw new BusinessException("行为类型不合法", ResultCode.PARAM_ERROR);
        }

        // 构建查询条件
        LambdaQueryWrapper<UserBehavior> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserBehavior::getUserId, userId)
                .eq(UserBehavior::getProductId, productId)
                .eq(UserBehavior::getBehaviorType, behaviorType)
                .orderByDesc(UserBehavior::getBehaviorTime)
                .last("LIMIT 1");

        // 找到最近的一条记录进行删除
        UserBehavior behavior = getOne(queryWrapper);
        if (behavior == null) {
            // 没有找到记录，返回false
            return false;
        }

        // 删除该记录
        return removeById(behavior.getId());
    }

    @Override
    public String getBehaviorTypeDesc(Integer behaviorType) {
        if (behaviorType == null) {
            return "未知行为";
        }

        switch (behaviorType) {
            case BEHAVIOR_VIEW:
                return "浏览";
            case BEHAVIOR_FAVORITE:
                return "收藏";
            case BEHAVIOR_CART:
                return "加购";
            case BEHAVIOR_PURCHASE:
                return "购买";
            case BEHAVIOR_REVIEW:
                return "评价";
            default:
                return "未知行为";
        }
    }
}




