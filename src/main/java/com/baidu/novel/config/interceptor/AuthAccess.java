package com.baidu.novel.config.interceptor;


import java.lang.annotation.*;


/***
 * 自定义注解类
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthAccess {
}
