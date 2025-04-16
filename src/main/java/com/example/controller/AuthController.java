package com.example.controller;

import com.example.common.Result;
import com.example.model.dto.CaptchaVO;
import com.example.model.dto.LoginDTO;
import com.example.model.dto.RegisterDTO;
import com.example.model.dto.UserLoginVO;
import com.example.service.UserService;
import com.example.util.CaptchaUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.UUID;

/**
 * 认证控制器
 */
@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final CaptchaUtil captchaUtil;

    /**
     * 用户登录
     *
     * @param loginDTO 登录参数
     * @return 登录结果
     */
    @PostMapping("/login")
    public Result<UserLoginVO> login(@RequestBody @Valid LoginDTO loginDTO) {
        log.info("用户登录: {}", loginDTO.getUsername());
        UserLoginVO userLoginVO = userService.login(loginDTO);
        return Result.success(userLoginVO, "登录成功");
    }

    /**
     * 用户注册
     *
     * @param registerDTO 注册参数
     * @return 注册结果，包含用户信息和token
     */
    @PostMapping("/register")
    public Result<UserLoginVO> register(@RequestBody @Valid RegisterDTO registerDTO) {
        log.info("用户注册: {}", registerDTO.getUsername());
        UserLoginVO userLoginVO = userService.register(registerDTO);
        return Result.success(userLoginVO, "注册成功");
    }

    /**
     * 用户登出
     *
     * @return 登出结果
     */
    @PostMapping("/logout")
    public Result<Void> logout(HttpServletRequest request) {
        // JWT登出不需要服务端操作，只需前端删除token
        // 未来可以实现黑名单token机制
        log.info("用户登出");
        return Result.success(null, "登出成功");
    }

    /**
     * 获取验证码
     *
     * @return 验证码结果
     */
    @GetMapping("/captcha")
    public Result<CaptchaVO> getCaptcha() {
        try {
            // 生成验证码标识
            String captchaKey = UUID.randomUUID().toString();
            // 生成验证码图片
            String captchaBase64 = captchaUtil.generateCaptcha(captchaKey);

            CaptchaVO captchaVO = CaptchaVO.builder()
                    .key(captchaKey)
                    .image(captchaBase64)
                    .build();
            return Result.success(captchaVO);
        } catch (Exception e) {
            return Result.error("获取验证码失败，请重试");
        }
    }
} 