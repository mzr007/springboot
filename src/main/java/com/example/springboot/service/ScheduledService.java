package com.example.springboot.service;

import com.example.springboot.RedisLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

//@Component
public class ScheduledService {
    private final Logger logger = LoggerFactory.getLogger(ScheduledService.class);
    @Autowired
    private RedisTemplate redisTemplate;

    @Scheduled(fixedRate = 5000)
    // lockPredix:项目前缀  完整的key:lockPrefix+lockKey两者都必填 timeOut单位为秒
    @RedisLock(lockPrefix ="springboot",lockKey = "testkey",timeOut = 5)
    public void scheduledTest1(){
            logger.info("定时任务1");
    }

    @Scheduled(fixedRate = 5000)
    @RedisLock(lockPrefix ="springboot",lockKey = "testkey",timeOut = 5)
    public void scheduledTest2(){
            logger.info("定时任务2");
    }
}
