package com.example.util;

import com.example.common.ResultCode;
import com.example.config.CaptchaConfig;
import com.example.exception.BusinessException;
import com.wf.captcha.base.Captcha;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * 验证码工具类
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CaptchaUtil {

    /**
     * Redis中验证码的前缀
     */
    private static final String CAPTCHA_PREFIX = "mall:captcha:";
    private final RedisTemplate<String, Object> redisTemplate;
    private final CaptchaConfig captchaConfig;

    /**
     * 生成验证码
     *
     * @param key 验证码标识，通常是用户的唯一标识（如：UUID）
     * @return 验证码图片的Base64编码
     */
    public String generateCaptcha(String key) {
        if (!StringUtils.hasText(key)) {
            throw new BusinessException("验证码标识不能为空", ResultCode.PARAM_ERROR);
        }

        try {
            // 获取验证码生成器实例
            Captcha captcha = captchaConfig.captcha();

            // 生成验证码文本
            String code = captcha.text();
            log.info("生成验证码: {}, 标识: {}", code, key);

            // 将验证码存入Redis
            String redisKey = CAPTCHA_PREFIX + key;
            redisTemplate.opsForValue().set(redisKey, code, CaptchaConfig.getExpireTime(), TimeUnit.SECONDS);

            // 返回验证码图片的Base64编码
            return captcha.toBase64();
        } catch (Exception e) {
            log.error("生成验证码失败", e);
            throw new BusinessException("生成验证码失败", ResultCode.SYSTEM_ERROR);
        }
    }

    /**
     * 验证验证码
     *
     * @param key  验证码标识
     * @param code 用户输入的验证码
     * @return 是否验证通过
     */
    public boolean verifyCaptcha(String key, String code) {
        if (!StringUtils.hasText(key) || !StringUtils.hasText(code)) {
            log.warn("验证码验证失败：key或code为空");
            return false;
        }

        try {
            // 从Redis获取验证码
            String redisKey = CAPTCHA_PREFIX + key;
            Object cacheCode = redisTemplate.opsForValue().get(redisKey);

            if (cacheCode == null) {
                log.warn("验证码验证失败：Redis中不存在该验证码或已过期，key={}", key);
                return false;
            }

            // 验证码是否匹配
            boolean isMatch = code.equalsIgnoreCase(cacheCode.toString());

            // 验证成功后删除Redis中的验证码
            redisTemplate.delete(redisKey);

            if (isMatch) {
                log.info("验证码验证成功：key={}", key);
            } else {
                log.warn("验证码验证失败：验证码不匹配，key={}", key);
            }

            return isMatch;
        } catch (Exception e) {
            log.error("验证码验证过程中发生错误", e);
            return false;
        }
    }

}