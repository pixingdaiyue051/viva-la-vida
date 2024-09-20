package com.tequeno.vivaboot.config.redis;

import com.tequeno.dto.HtJmsRedisModel;
import com.tequeno.enums.JedisKeyPrefixEnum;
import com.tequeno.service.IDemoService;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.api.listener.StatusListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 不依赖spring的jedis服务，单例模式 使用jedisPool支持多线程
 */
@Component
public class RedissonTask {

    private final static Logger log = LoggerFactory.getLogger(RedissonTask.class);

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
                    log.info("redisson 当前时间[{}],发送时间[{}],相差[{}],预设延迟[{}]", currentTimeMillis, take.getTimestamp(), currentTimeMillis - take.getTimestamp(), take.getDelay());
                } catch (InterruptedException e) {
                    log.error("redisson 获取队列任务异常", e);
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
                    log.info("redisson [{}] 订阅上线", channel);
                }

                @Override
                public void onUnsubscribe(String channel) {
                    log.info("redisson [{}] 订阅下线", channel);
                }
            });

            topic.addListener(HtJmsRedisModel.class, (c, m) -> log.info("redisson channel [{}] 收到 [{}]", c, m.getCode()));

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
}