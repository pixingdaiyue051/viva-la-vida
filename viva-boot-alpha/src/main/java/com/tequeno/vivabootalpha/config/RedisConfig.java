package com.tequeno.vivabootalpha.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisConfig {

    @Value("${redis.host}")
    private String host;

    @Value("${redis.port}")
    private int port;

    @Value("${redis.database}")
    private int database;

    @Value("${redis.password}")
    private String password;

//    @Bean
//    public RedissonClient redissonConfig() {
//        String address = String.format("redis://%s:%d", host, port);
//        Config config = new Config();
//        config.useSingleServer()
//                .setAddress(address)
//                .setPassword(password)
//                .setDatabase(database);
//        return Redisson.create(config);
//    }
}
