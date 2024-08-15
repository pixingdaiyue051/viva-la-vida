package com.tequeno;

import com.tequeno.vivaboot.config.redis.JedisUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JedisTest {


    private final static Logger logger = LoggerFactory.getLogger(JedisTest.class);


    @Test
    public void run() {
        JedisUtil jedisUtil = JedisUtil.getInstance();


        long l1 = System.currentTimeMillis();

        String lockKey = "test";
        String token = String.valueOf(l1);
        boolean result = jedisUtil.luaTryLock(lockKey, token, 3000L);

//        boolean result = jedisUtil.luaReleaseLock(lockKey, token);

        long l2 = System.currentTimeMillis();
        logger.info("redis执行[{}]ms,[{}]", l2 - l1, result);
    }
}
