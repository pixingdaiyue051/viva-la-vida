package com.tequeno.conutil;

import io.netty.util.HashedWheelTimer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.concurrent.*;

/**
 * 延时消息 定时任务
 */
public class DelayTaskHandler {

    private final static Logger logger = LoggerFactory.getLogger(DelayTaskHandler.class);

    /**
     * 本地服务内存 重启后失效 不推荐
     * 无限循环 模拟定时任务
     */
    public void infiniteLoop() {
        logger.info("infiniteLoop");
        // 存放定时任务
        final Map<String, Long> taskMap = new HashMap<>();

        // 添加定时任务
        final Instant now = Instant.now();
        logger.info("infiniteLoop 添加任务数据");
        taskMap.put("task-2", now.plusMillis(3000L).toEpochMilli());
        taskMap.put("task-4", now.plusMillis(1000L).toEpochMilli());
        taskMap.put("task-3", now.plusMillis(5000L).toEpochMilli());
        taskMap.put("task-1", now.plusMillis(10000L).toEpochMilli());
        taskMap.put("task-5", now.plusMillis(16000L).toEpochMilli());

        Long itemLong;
        do {
            Iterator<Map.Entry<String, Long>> it = taskMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, Long> entry = it.next();
                itemLong = entry.getValue();
                final long milli = Instant.now().toEpochMilli();
                // 有任务需要执行
                if (milli >= itemLong) {
                    // 延迟任务，业务逻辑执行
                    logger.info("{}执行任务{}", Thread.currentThread().getName(), entry.getKey());
                    // 删除任务
                    it.remove();
                    logger.info("剩余任务数:{}", taskMap.size());
                }
            }
        } while (!taskMap.isEmpty());
    }

    /**
     * 本地服务内存 重启后失效 不推荐
     * DelayQueue
     */
    public void delayedQueue() {
        logger.info("delayedQueue");
        DelayQueue<DelayedQueueEl> dq = new DelayQueue<>();
        logger.info("delayedQueue 添加任务数据");
        dq.add(new DelayedQueueEl("task-2", 3000L));
        dq.add(new DelayedQueueEl("task-4", 1000L));
        dq.add(new DelayedQueueEl("task-3", 5000L));
        dq.add(new DelayedQueueEl("task-1", 10000L));
        dq.add(new DelayedQueueEl("task-5", 16000L));

        while (!dq.isEmpty()) {
            try {
                final DelayedQueueEl taken = dq.take();
                logger.info("{}执行任务{}", Thread.currentThread().getName(), taken.getName());
                logger.info("剩余任务数:{}", dq.size());
            } catch (InterruptedException e) {
                logger.error("delayedQueue 异常", e);
            }
        }
    }

    /**
     * 本地服务内存 重启后失效 不推荐
     * 使用单独的timerThread阻塞式执行任务
     * timerTask Timer定时器
     */
    public void timerTask() {
        logger.info("timerTask");
        Timer timer = new Timer();
        logger.info("timerTask 添加任务数据");
        CountDownLatch count = new CountDownLatch(5);
        timer.schedule(new DelayedTimerTaskEl("task-2", count), 3000L);
        timer.schedule(new DelayedTimerTaskEl("task-4", count), 1000L);
        timer.schedule(new DelayedTimerTaskEl("task-3", count), 5000L);
        timer.schedule(new DelayedTimerTaskEl("task-1", count), 10000L);
        timer.schedule(new DelayedTimerTaskEl("task-5", count), 16000L);

        try {
            count.await();
            timer.cancel();
            timer.purge();
        } catch (InterruptedException e) {
            logger.error("timerTask 异常", e);
        }
    }

    /**
     * 本地服务内存 重启后失效 不推荐
     * ScheduledExecutorService
     */
    public void scheduledExecutor() {
        logger.info("scheduledExecutor");
        final ScheduledExecutorService service = Executors.newScheduledThreadPool(10);
        CountDownLatch count = new CountDownLatch(5);
        service.schedule(new DelayedExecutorEl("task-2", count), 3000L, TimeUnit.MILLISECONDS);
        service.schedule(new DelayedExecutorEl("task-4", count), 1000L, TimeUnit.MILLISECONDS);
        service.schedule(new DelayedExecutorEl("task-3", count), 5000L, TimeUnit.MILLISECONDS);
        service.schedule(new DelayedExecutorEl("task-1", count), 10000L, TimeUnit.MILLISECONDS);
        service.schedule(new DelayedExecutorEl("task-5", count), 16000L, TimeUnit.MILLISECONDS);
        service.shutdown();

        try {
            count.await();
        } catch (InterruptedException e) {
            logger.error("scheduledExecutor 异常", e);
        }

//        while (!service.isTerminated()) ;
    }

    /**
     * 本地服务内存 重启后失效 不推荐
     * Netty hashedWheelTimer 的延迟任务
     * <p>
     * Netty 是由 JBOSS 提供的一个 Java 开源框架，它是一个基于 NIO 的客户、服务器端的编程框架，
     * 使用 Netty 可以确保你快速和简单的开发出一个网络应用，例如实现了某种协议的客户、服务端应用。
     * Netty 相当于简化和流线化了网络应用的编程开发过程，例如：基于 TCP 和 UDP 的 socket 服务开发
     * <p>
     * HashedWheelTimer 是使用定时轮实现的，定时轮其实就是一种环型的数据结构，
     * 可以把它想象成一个时钟，分成了许多格子，每个格子代表一定的时间，
     * 在这个格子上用一个链表来保存要执行的超时任务，同时有一个指针一格一格的走，
     * 走到那个格子时就执行格子对应的延迟任务
     * <p>
     * 需要使用netty全功能需要导入netty-all
     * 需要使用工具类只需要导入netty-common
     * TODO 待改进验证
     */
    public void hashedWheelTimer() {
        logger.info("hashedWheelTimer");
        HashedWheelTimer timer = new HashedWheelTimer(1000, TimeUnit.MILLISECONDS, 8);
        logger.info("hashedWheelTimer 添加任务数据");
        CountDownLatch count = new CountDownLatch(5);
        timer.newTimeout(new DelayedHashWheelEl("task-2", count), 3000L, TimeUnit.MILLISECONDS);
        timer.newTimeout(new DelayedHashWheelEl("task-4", count), 1000L, TimeUnit.MILLISECONDS);
        timer.newTimeout(new DelayedHashWheelEl("task-3", count), 5000L, TimeUnit.MILLISECONDS);
        timer.newTimeout(new DelayedHashWheelEl("task-1", count), 10000L, TimeUnit.MILLISECONDS);
        timer.newTimeout(new DelayedHashWheelEl("task-5", count), 16000L, TimeUnit.MILLISECONDS);

        try {
            count.await();
            timer.stop();
        } catch (InterruptedException e) {
            logger.error("hashedWheelTimer 异常", e);
        }
//        while (timer.pendingTimeouts() > 0) ;
    }

}