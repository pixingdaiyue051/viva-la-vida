package com.tequeno.conutil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisUtil {

    private static JedisUtil INSTANCE = null;
    private JedisPool pool = null;

    public static JedisUtil getInstance() {
        if (null == INSTANCE) {
            synchronized (JedisUtil.class) {
                if (null == INSTANCE) {
                    INSTANCE = new JedisUtil();
                    INSTANCE.init();
                }
            }
        }
        return INSTANCE;
    }

    private void init() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMinIdle(0);
        jedisPoolConfig.setMaxIdle(8);
        jedisPoolConfig.setMaxTotal(8);
        jedisPoolConfig.setMaxWaitMillis(10000L);
        jedisPoolConfig.setBlockWhenExhausted(false);
        jedisPoolConfig.setJmxEnabled(false);
        pool = new JedisPool(jedisPoolConfig, "127.0.0.1", 6379, 10000, null, 0);
    }

    public Jedis getJedis() {
        return pool.getResource();
    }
}