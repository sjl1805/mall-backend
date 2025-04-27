package com.example.config;

import com.example.interceptor.JwtInterceptor;
import com.example.interceptor.RoleInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

/**
 * Web MVC配置
 */
@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final JwtInterceptor jwtInterceptor;
    private final RoleInterceptor roleInterceptor;
    @Value("${file.upload.path}")
    private String uploadPath;

    /**
     * 配置静态资源映射
     */
    @Override
    public void addResourceHandlers(@NonNull ResourceHandlerRegistry registry) {
        // 将上传文件夹映射为静态资源路径
        String uploadPath = new File(this.uploadPath).getAbsolutePath();
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + uploadPath + "/");

        // 添加Swagger UI资源映射
        registry.addResourceHandler("/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/springdoc-openapi-ui/");
    }

    /**
     * 配置拦截器
     */
    @Override
    public void addInterceptors(@NonNull InterceptorRegistry registry) {
        // 添加JWT拦截器
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        // 无需登录即可访问的路径
                        "/auth/login",
                        "/auth/register",
                        "/auth/captcha",
                        "/captcha",
                        // 静态资源
                        "/uploads/**",
                        // 公开API
                        "/product/**",
                        "/category/**",
                        // 公开评价API，但不包括需要登录的用户评价接口
                        "/review/product/**",
                        "/review/stats/**",
                        "/review/latest",
                        // 文件查看和信息接口
                        "/file/view",
                        "/file/info",
                        // OpenAPI (Swagger)路径
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/api-docs/**",
                        "/v3/api-docs/**",
                        "/swagger-resources/**",
                        "/webjars/**",
                        // 错误页面
                        "/error"
                );

        // 添加角色拦截器
        registry.addInterceptor(roleInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        // 无需登录即可访问的路径
                        "/auth/login",
                        "/auth/register",
                        "/auth/captcha",
                        "/captcha",
                        // 静态资源
                        "/uploads/**",
                        // 公开API
                        "/product/**",
                        "/category/**",
                        // 公开评价API，但不包括需要登录的用户评价接口
                        "/review/product/**",
                        "/review/stats/**",
                        "/review/latest",
                        // 文件查看和信息接口
                        "/file/view",
                        "/file/info",
                        // OpenAPI (Swagger)路径
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/api-docs/**",
                        "/v3/api-docs/**",
                        "/swagger-resources/**",
                        "/webjars/**",
                        // 错误页面
                        "/error"
                );
    }
} 