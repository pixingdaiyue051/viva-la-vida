package com.tequeno.vivaboot.service;

import com.tequeno.annos.AntiRepeatAnno;
import com.tequeno.dto.HtJmsRedisModel;
import com.tequeno.dto.HtJmsRocketModel;
import com.tequeno.enums.JedisKeyPrefixEnum;
import com.tequeno.service.IDemoService;
import com.tequeno.utils.HtCommonMethodUtil;
import com.tequeno.utils.TimeUtil;
import org.redisson.api.*;
import org.redisson.api.listener.StatusListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class RedissonService {

    private final static Logger logger = LoggerFactory.getLogger(RedissonService.class);

    @Resource
    private RedissonClient redisson;

    @Resource
    private IDemoService demoService;

    @Resource
    private ThreadPoolTaskExecutor pool;

    private RBlockingQueue<HtJmsRedisModel> blockingQueue;

    private RDelayedQueue<HtJmsRedisModel> delayedQueue;

    private RTopic topic;

//    @PostConstruct
    public void postConstruct() {

//        // 注册服务
//        pool.execute(() -> {
//            RRemoteService remoteService = redisson.getRemoteService(JedisKeyPrefixEnum.REMOTE.getPrefix());
//            remoteService.register(IDemoService.class, demoService);
//        });

        // redis延迟队列,实现消息延迟投递
        pool.execute(() -> {
            blockingQueue = redisson.getBlockingQueue(JedisKeyPrefixEnum.QUEUE.getPrefix());
            while (true) {
                try {
                    HtJmsRedisModel take = blockingQueue.take();
                    long currentTimeMillis = System.currentTimeMillis();
                    logger.info("redisson 当前时间[{}],发送时间[{}],相差[{}],预设延迟[{}]", currentTimeMillis, take.getTimestamp(), currentTimeMillis - take.getTimestamp(), take.getDelay());
                } catch (InterruptedException e) {
                    logger.error("redisson 获取队列任务异常", e);
                    break;
                }
            }
        });

        // redis订阅发布机制,实现消息转发分流
        pool.execute(() -> {
            RTopic topic = redisson.getTopic(JedisKeyPrefixEnum.TOPIC.getPrefix());
            topic.addListener(new StatusListener() {
                @Override
                public void onSubscribe(String channel) {
                    logger.info("redisson [{}] 订阅上线", channel);
                }

                @Override
                public void onUnsubscribe(String channel) {
                    logger.info("redisson [{}] 订阅下线", channel);
                }
            });

            topic.addListener(HtJmsRocketModel.class, (c, m) -> logger.info("redisson channel [{}] 收到 [{}]", c, m.getCode()));

        });
    }

    /**
     * redis 发布延迟消息
     *
     * @param model
     */
    public void sendRedisDelayMsg(HtJmsRedisModel model) {
        if (null == delayedQueue) {
            delayedQueue = redisson.getDelayedQueue(blockingQueue);
        }
        pool.execute(() -> {
            model.setTimestamp(System.currentTimeMillis());
            delayedQueue.offer(model, model.getDelay(), TimeUnit.MILLISECONDS);
        });
    }

    /**
     * redis 发布订阅消息
     *
     * @param model
     */
    public void publishRedisMsg(HtJmsRedisModel model) {
        if (null == topic) {
            topic = redisson.getTopic(JedisKeyPrefixEnum.TOPIC.getPrefix());
        }
        pool.execute(() -> {
            model.setTimestamp(System.currentTimeMillis());
            topic.publish(model);
        });
    }

    public long seq() {
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
        return nextId;
    }

    public void lock() {
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
    }

    @AntiRepeatAnno
    public void lock1() {
        logger.info("加锁成功");
    }

    public void bloom() {

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

    }

}
