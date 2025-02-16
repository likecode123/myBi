package com.yupi.springbootinit.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

@Configuration
public class ThreadPoolExcutorConfig {
    @Bean
    public ThreadPoolExecutor threadPoolExecutor() {
        //自定义线程工厂
        ThreadFactory threadFactory =new ThreadFactory() {
            private int count =1 ;
            @Override
            public Thread newThread(@NotNull Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("myThread-"+count);
                count++;
                return thread;
            }

        };

//自定义线程池
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 4, 100,
        TimeUnit.SECONDS,
             new    ArrayBlockingQueue(4),
                threadFactory
        );


        return threadPoolExecutor;
    }

}
