package com.example.springboot;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface RedisLock {
    //redisKEY的项目前缀
    String lockPrefix() default "";
    String lockKey() default "";
    long timeOut() default 5;
}
