package com.example.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

/**
 * MyBatis-Plus配置类
 */
@Slf4j
@Configuration
@MapperScan("com.example.mapper")
public class MybatisPlusConfig {

    /**
     * 配置MyBatis-Plus插件（分页等）
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 添加分页插件
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

    /**
     * 配置字段自动填充
     */
    @Bean
    public MetaObjectHandler metaObjectHandler() {
        return new MetaObjectHandler() {
            @Override
            public void insertFill(MetaObject metaObject) {
                log.info("开始自动填充insert字段");
                // 创建时间和更新时间自动填充
                this.strictInsertFill(metaObject, "createTime", Date.class, new Date());
                this.strictInsertFill(metaObject, "updateTime", Date.class, new Date());
                // 逻辑删除默认值
                this.strictInsertFill(metaObject, "deleted", Integer.class, 0);
            }

            @Override
            public void updateFill(MetaObject metaObject) {
                log.info("开始自动填充update字段");
                // 更新时间自动填充
                this.strictUpdateFill(metaObject, "updateTime", Date.class, new Date());
            }
        };
    }
} 