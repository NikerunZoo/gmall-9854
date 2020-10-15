package com.atguigu.gmall.index.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @author NikerunZoo
 * @date 2020/10/9 0009 16:43
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GmallCache {

    /*
    * 缓存的前缀
    * */
    @AliasFor("value")
    String prefix() default "";
    @AliasFor("prefix")
    String value() default "";

    /*
    * 缓存的过期时间以分为单位
    * */
    int timeout() default 5;

    /**
     * 防止缓存雪崩指定的随机时间
     * @return
     */
    int random() default 5;

}
