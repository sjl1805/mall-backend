package com.example.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * 订单号工具类
 */
public class OrderNoUtil {

    /**
     * 订单号日期格式
     */
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    /**
     * 随机数生成器
     */
    private static final Random RANDOM = new Random();

    /**
     * 生成订单号
     * 格式: 年月日时分秒 + 6位随机数
     *
     * @return 订单号
     */
    public static String generateOrderNo() {
        // 获取当前时间
        String nowTime = LocalDateTime.now().format(FORMATTER);

        // 生成6位随机数
        String randomNumber = String.format("%06d", RANDOM.nextInt(1000000));

        // 组合订单号
        return nowTime + randomNumber;
    }

}