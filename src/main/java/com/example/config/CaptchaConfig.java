package com.example.config;

import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.awt.*;
import java.io.IOException;

/**
 * 验证码配置类
 */
@Setter
@Slf4j
@Configuration
@ConfigurationProperties(prefix = "captcha")
public class CaptchaConfig {

    /**
     * 验证码过期时间（秒）
     */
    @Getter
    private static Integer expireTime = 120;
    /**
     * 验证码类型：1-算术验证码, 2-字符验证码
     */
    private Integer type = 2; // 默认使用字符验证码，避免脚本引擎问题
    /**
     * 验证码宽度
     */
    private Integer width = 130;
    /**
     * 验证码高度
     */
    private Integer height = 48;
    /**
     * 验证码长度
     */
    private Integer length = 4;

    public void setExpireTime(Integer expireTime) {
        CaptchaConfig.expireTime = expireTime;
    }

    /**
     * 获取验证码生成器实例
     *
     * @return 验证码实例
     */
    public Captcha captcha() {
        Captcha captcha;

        if (this.type == 1) {
            // 算术验证码
            captcha = new ArithmeticCaptcha(this.width, this.height);
            // 几位数运算
            captcha.setLen(this.length);
        } else {
            // 字符验证码
            captcha = new SpecCaptcha(this.width, this.height);
            captcha.setLen(this.length);
            // 设置字符类型
            captcha.setCharType(Captcha.TYPE_DEFAULT);
        }

        // 设置字体
        try {
            captcha.setFont(Captcha.FONT_1);
        } catch (IOException | FontFormatException e) {
            log.error("设置验证码字体出错", e);
        }

        return captcha;
    }

}