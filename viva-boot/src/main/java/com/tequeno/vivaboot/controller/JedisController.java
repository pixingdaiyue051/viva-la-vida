package com.tequeno.vivaboot.controller;

import com.tequeno.dto.HtResultModel;
import com.tequeno.utils.HtResultUtil;
import com.tequeno.utils.TimeUtil;
import com.tequeno.vivaboot.config.redis.JedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("jedis")
@Slf4j
public class JedisController {

    @RequestMapping("seq")
    public HtResultModel seq() {
        long first = 24000;

        JedisUtil jedisUtil = JedisUtil.getInstance();
        Object out = jedisUtil.luaGetSequenceNum(first);

        log.info("序列号 {}", out);
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
            log.info("加锁成功");
            return HtResultUtil.success(TimeUtil.now());
        } else {
            log.info("加锁失败");
            return HtResultUtil.fail();
        }
    }
}
