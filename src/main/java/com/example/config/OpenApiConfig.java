package com.example.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI (Swagger) 配置
 */
@Configuration
public class OpenApiConfig {

    /**
     * 配置OpenAPI基本信息
     */
    @Bean
    public OpenAPI mallOpenAPI() {
        // 添加JWT token认证信息
        final String securitySchemeName = "JWT认证";

        return new OpenAPI()
                .info(new Info()
                        .title("商城系统API文档")
                        .description("商城系统后端API接口文档")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("开发团队")
                                .email("developer@example.com")
                                .url("https://www.example.com"))
                        .license(new License()
                                .name("MIT")
                                .url("https://opensource.org/licenses/MIT")))
                // 添加JWT Bearer Authentication全局配置
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                // 配置JWT安全方案
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                                .name(securitySchemeName)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .description("在下方输入Bearer Token，格式：Bearer {token}")));
    }
} 