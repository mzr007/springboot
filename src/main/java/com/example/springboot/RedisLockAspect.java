package com.example.springboot;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
public class RedisLockAspect {
    private final Logger logger = LoggerFactory.getLogger(RedisLockAspect.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @Pointcut("@annotation(com.example.springboot.RedisLock)")
    public void redisLockAspect() {
    }

    @Around("redisLockAspect()")
    public void lockAroundAction(ProceedingJoinPoint proceeding) throws Exception {

        //获取注解中的参数
        Map<String, Object> annotationArgs = this.getAnnotationArgs(proceeding);
        String lockPrefix = (String) annotationArgs.get("LOCK_PRE_FIX");
        String key = (String) annotationArgs.get("LOCK_KEY");
        long expire = (long) annotationArgs.get("TIME_OUT");
        if (StringUtils.isEmpty(lockPrefix) || StringUtils.isEmpty(key)) {
            logger.error("RedisLock锁前缀,锁名未设置");
            throw new RuntimeException("RedisLock锁前缀,锁名未设置");
        }
        //获取redis锁
        Boolean flag = redisTemplate.opsForValue().setIfAbsent(lockPrefix+key,"1",expire,TimeUnit.SECONDS);
        if(flag) {
            try {
                proceeding.proceed();
            } catch (Throwable throwable) {
                logger.error("分布式锁执行发生异常" + throwable.getMessage());
                throw new RuntimeException("分布式锁执行发生异常" + throwable.getMessage(), throwable);
            }
        } else {
            logger.info("其他系统正在执行此项任务");
        }

    }

    /**
     * 获取锁参数
     *
     * @param proceeding
     * @return
     */
    private Map<String, Object> getAnnotationArgs(ProceedingJoinPoint proceeding) {
        Class target = proceeding.getTarget().getClass();
        Method[] methods = target.getMethods();
        String methodName = proceeding.getSignature().getName();
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Map<String, Object> result = new HashMap<String, Object>();
                RedisLock redisLock = method.getAnnotation(RedisLock.class);
                result.put("LOCK_PRE_FIX", redisLock.lockPrefix());
                result.put("LOCK_KEY", redisLock.lockKey());
                result.put("TIME_OUT", redisLock.timeOut());
                return result;
            }
        }
        return null;
    }
}
