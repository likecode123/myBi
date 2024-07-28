package com.yupi.springbootinit.manager;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class RedisLimiterManagerTest {
    @Resource
    private RedisLimiterManager redisLimiterManager;

    @Test
    void doRateLimit() throws InterruptedException {
        String userId="1";
        for (int i =0;i<2;i++){
            redisLimiterManager.doRateLimit(userId);
            System.out.println("请求成功");
        }

        Thread.sleep(2000);
        for (int i =0;i<=3;i++){
            redisLimiterManager.doRateLimit(userId);
            System.out.println("本次请求成功");
        }
    }
}