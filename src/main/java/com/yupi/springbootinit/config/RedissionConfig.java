package com.yupi.springbootinit.config;

import lombok.Data;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix="spring.redis")
@Data
public class RedissionConfig {
    private Integer database;

    private String host;
    private String password;

    private Integer port;

    @Bean
    public RedissonClient getRedissonClient() {
        // 1.创建配置对象

        Config config = new Config();
        // 添加单机Redisson配置
        config.useSingleServer()
                // 设置数据库1719189991361474562_0.03448077337621491
                .setDatabase(database)
                // 设置redis的地址
                .setAddress("redis://" + host + ":" + port)
//         设置redis的密码(redis有密码才设置)
                        .setPassword(password);

        // 2.创建Redisson实例1719189991361474562_0.40829706641548347
        RedissonClient redisson = Redisson.create(config);
        return redisson;
    }

}
