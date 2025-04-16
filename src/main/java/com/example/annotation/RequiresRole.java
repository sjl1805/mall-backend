package com.example.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 需要特定角色访问的注解
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiresRole {

    /**
     * 需要的角色值，默认为空，表示任何角色都可访问 1为管理员 2为普通用户
     */
    int[] value() default {};
} 