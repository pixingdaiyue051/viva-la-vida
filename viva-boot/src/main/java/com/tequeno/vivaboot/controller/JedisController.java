package com.tequeno.vivaboot.controller;

import com.tequeno.dto.HtResultModel;
import com.tequeno.utils.HtResultUtil;
import com.tequeno.utils.TimeUtil;
import com.tequeno.vivaboot.config.redis.JedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("jedis")
public class JedisController {

    private final static Logger logger = LoggerFactory.getLogger(JedisController.class);

    @RequestMapping("seq")
    public HtResultModel seq() {
        long first = 24000;

        JedisUtil jedisUtil = JedisUtil.getInstance();
        Object out = jedisUtil.luaGetSequenceNum(first);

        logger.info("序列号 {}", out);
        return HtResultUtil.success(out);
    }

    @RequestMapping("lock")
    public HtResultModel lock() {
        String key = "test";
        long expire = 650000L;

        JedisUtil jedisUtil = JedisUtil.getInstance();
        String token = String.valueOf(System.currentTimeMillis());
        boolean result = jedisUtil.luaTryLock(key, token, expire);

        if (result) {
            logger.info("加锁成功");
            return HtResultUtil.success(TimeUtil.now());
        } else {
            logger.info("加锁失败");
            return HtResultUtil.fail();
        }
    }
}
