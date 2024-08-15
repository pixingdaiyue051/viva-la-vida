package com.tequeno.vivaboot.config.redis;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfig {

    @Value("${redis.host}")
    private String host;

    @Value("${redis.port}")
    private int port;

    @Value("${redis.database}")
    private int database;

    @Value("${redis.timeout}")
    private int timeout;

    @Value("${redis.password}")
    private String password;

    @Value("${redis.block-when-exhausted}")
    private boolean blockWhenExhausted;

    @Value("${redis.jmx-enabled}")
    private boolean jmxEnabled;

    @Value("${redis.min-idle}")
    private int minIdle;

    @Value("${redis.max-idle}")
    private int maxIdle;

    @Value("${redis.max-active}")
    private int maxTotal;

    @Value("${redis.max-wait}")
    private long maxWaitMillis;

//    @Bean
//    public JedisPool jedisPoolConfig() {
//        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
//        jedisPoolConfig.setMinIdle(minIdle);
//        jedisPoolConfig.setMaxIdle(maxIdle);
//        jedisPoolConfig.setMaxTotal(maxTotal);
//        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
//        jedisPoolConfig.setBlockWhenExhausted(blockWhenExhausted);
//        jedisPoolConfig.setJmxEnabled(jmxEnabled);
//        return new JedisPool(jedisPoolConfig, host, port, timeout, password, database);
//    }


    @Bean
    public RedissonClient redissonConfig() {
        String address = String.format("redis://%s:%d", host, port);
        Config config = new Config();
        config.useSingleServer()
                .setAddress(address)
                .setPassword(password)
                .setDatabase(database);
        return Redisson.create(config);
    }
}
