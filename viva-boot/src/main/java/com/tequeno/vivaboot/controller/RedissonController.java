package com.tequeno.vivaboot.controller;

import com.tequeno.dto.HtJmsRedisModel;
import com.tequeno.dto.HtResultModel;
import com.tequeno.enums.JedisKeyPrefixEnum;
import com.tequeno.utils.HtCommonMethodUtil;
import com.tequeno.utils.HtResultUtil;
import com.tequeno.utils.TimeUtil;
import com.tequeno.vivaboot.config.redis.RedissonTask;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RIdGenerator;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("redisson")
public class RedissonController {

    private final static Logger logger = LoggerFactory.getLogger(RedissonController.class);

    @Resource
    private RedissonClient redisson;

    @Resource
    private RedissonTask redissonTask;

    @RequestMapping("seq")
    public HtResultModel seq() {

        long first = 24000;
        long step = 1;

        RIdGenerator idGenerator = redisson.getIdGenerator(JedisKeyPrefixEnum.SEQ.assemblyKey(TimeUtil.nowDateNum()));
        boolean b = idGenerator.tryInit(first, step);
        if (b) {
            Duration i = Duration.ofDays(1);
            idGenerator.expire(i);
        }

        long nextId = idGenerator.nextId();

        logger.info("序列号 {} {}", b, nextId);
        return HtResultUtil.success(nextId);
    }

    @RequestMapping("lock")
//    @AntiRepeatAnno
    public HtResultModel lock() {
        try {
            String key = "test";
            long expire = 65000L;
            RLock lock = redisson.getLock(JedisKeyPrefixEnum.LOCK.assemblyKey(key));
            boolean result = lock.tryLock(0, expire, TimeUnit.MILLISECONDS);
            if (result) {
                logger.info("加锁成功");
            } else {
                logger.info("加锁失败");
            }
        } catch (Exception e) {
            logger.error("加锁失败", e);
        }
        return HtResultUtil.success(TimeUtil.now());
    }

    @RequestMapping("delay")
    public HtResultModel delay(HtJmsRedisModel model) {
        redissonTask.sendRedisDelayMsg(model);
        return HtResultUtil.success(TimeUtil.now());
    }

    @RequestMapping("pub")
    public HtResultModel pub(HtJmsRedisModel model) {
        redissonTask.publishRedisMsg(model);
        return HtResultUtil.success(TimeUtil.now());
    }

    @RequestMapping("act")
    public HtResultModel act() {

        RBloomFilter<String> bloomFilter = redisson.getBloomFilter(JedisKeyPrefixEnum.BLOOM.getPrefix());
        bloomFilter.tryInit(100000, 0.01d);
        long size = bloomFilter.count();
        if (size < 1) {
            List<String> list = IntStream.range(0, 1000)
                    .boxed()
                    .map(i -> HtCommonMethodUtil.getDefaultRandomStr())
                    .collect(Collectors.toList());
            bloomFilter.add(list);
        }

        boolean contained = bloomFilter.contains("123r");
        logger.info("包含:{}", contained);

        return HtResultUtil.success(TimeUtil.now());
    }
}
