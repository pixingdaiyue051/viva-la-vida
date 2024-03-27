package com.tequeno.conutil;

import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import redis.clients.jedis.Jedis;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 延时消息 定时任务
 */
public class DelayTaskHandler {

    /**
     * 无限循环 模拟定时任务
     */
    @Deprecated
    public void infiniteLoop() {
        System.out.println("infiniteLoop");
        // 存放定时任务
        final Map<String, Long> taskMap = new HashMap<>();

        // 添加定时任务
        final Instant now = Instant.now();
        System.out.println("任务压栈task-%,启动时间:" + now.toEpochMilli());
        taskMap.put("task-3", now.plusSeconds(5).toEpochMilli());
        taskMap.put("task-1", now.plusSeconds(1).toEpochMilli());
        taskMap.put("task-4", now.plusSeconds(10).toEpochMilli());
        taskMap.put("task-2", now.plusSeconds(3).toEpochMilli());
        taskMap.put("task-5", now.plusSeconds(16).toEpochMilli());

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
                    System.out.printf("%s执行任务%s,执行时间:%s", Thread.currentThread().getName(), entry.getKey(), milli);
                    // 删除任务
                    it.remove();
                    System.out.println("剩余任务数:" + taskMap.size());
                }
            }
        } while (taskMap.size() != 0);
    }

    /**
     * timerTask Timer定时器
     */
    public void timerTask() {
        System.out.println("timerTask");
        System.out.println("任务压栈task-%,启动时间:" + System.currentTimeMillis());

        Timer timer = new Timer();
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            TimerTask t = new TimerTask() {
                @Override
                public void run() {
                    System.out.printf("%s执行,执行时间:%s", Thread.currentThread().getName(), System.currentTimeMillis());
                    System.out.println();
                    if (finalI == 4) {
                        timer.cancel();
                        timer.purge();
                    }
                }
            };
            timer.schedule(t, (i + 1) * 3000L);
        }
    }

    /**
     * DelayQueue
     * 延时队列
     */
    public void delayedQueue() {
        System.out.println("delayedQueue");
        System.out.println("任务压栈task-%,启动时间:" + System.currentTimeMillis());
        DelayQueue<QueueDelayEl> dq = new DelayQueue<>();
        dq.add(new QueueDelayEl("task-2", 3000L));
        dq.add(new QueueDelayEl("task-4", 1000L));
        dq.add(new QueueDelayEl("task-3", 5000L));
        dq.add(new QueueDelayEl("task-1", 10000L));
        dq.add(new QueueDelayEl("task-5", 16000L));

        while (!dq.isEmpty()) {
            try {
                final QueueDelayEl taken = dq.take();
                System.out.printf("%s执行任务%s,执行时间:%s", Thread.currentThread().getName(), taken.getName(), taken.getDelayTime());
                System.out.println("剩余任务数:" + dq.size());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * ScheduledExecutorService
     */
    public void scheduledExecutor() {
        System.out.println("scheduledExecutor");
        System.out.println("thread pool task,启动时间:" + System.currentTimeMillis());
        final ScheduledExecutorService service = Executors.newScheduledThreadPool(10);
        service.schedule(this::scheduledPof, 3, TimeUnit.SECONDS);
        service.schedule(this::scheduledPof, 10, TimeUnit.SECONDS);
        service.schedule(this::scheduledPof, 1, TimeUnit.SECONDS);
        service.schedule(this::scheduledPof, 5, TimeUnit.SECONDS);
        service.schedule(this::scheduledPof, 16, TimeUnit.SECONDS);
        service.shutdown();
    }

    private void scheduledPof() {
        System.out.printf("-%s,执行时间:%s", Thread.currentThread().getName(), System.currentTimeMillis());
        System.out.println();
    }

    /**
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
        System.out.println("hashedWheelTimer");
        System.out.println("hashedWheelTimer,启动时间:" + System.currentTimeMillis());
        HashedWheelTimer timer = new HashedWheelTimer(1, TimeUnit.SECONDS, 8); // 时间轮中的槽数
        timer.newTimeout(this::timerPof, 3, TimeUnit.SECONDS);
        timer.newTimeout(this::timerPof, 10, TimeUnit.SECONDS);
        timer.newTimeout(this::timerPof, 1, TimeUnit.SECONDS);
        timer.newTimeout(this::timerPof, 5, TimeUnit.SECONDS);
        timer.newTimeout(this::timerPof, 16, TimeUnit.SECONDS);
        while (true) {
            if (timer.pendingTimeouts() <= 0) {
                timer.stop();
                break;
            }
        }
    }

    private void timerPof(Timeout timeout) {
        System.out.printf("-%s,执行时间:%s", Thread.currentThread().getName(), System.currentTimeMillis());
        System.out.println();
    }

    /**
     * redis zset
     */
    @Deprecated
    public void redisZset() {
        System.out.println("redisZset");
        final Instant now = Instant.now();
        System.out.println("任务压栈task-%,启动时间:" + now.toEpochMilli());
        String key = "test:z1";
        final Jedis jedis = JedisUtil.getInstance().getJedis();
        jedis.zadd(key, now.plusSeconds(5).toEpochMilli(), "task-3");
        jedis.zadd(key, now.plusSeconds(1).toEpochMilli(), "task-1");
        jedis.zadd(key, now.plusSeconds(10).toEpochMilli(), "task-4");
        jedis.zadd(key, now.plusSeconds(3).toEpochMilli(), "task-2");
        jedis.zadd(key, now.plusSeconds(16).toEpochMilli(), "task-5");

        while (true) {
            // 当前时间
            Instant nowInstant = Instant.now();
            long lastSecond = nowInstant.plusSeconds(-1).toEpochMilli(); // 上一秒时间
            long nowSecond = nowInstant.toEpochMilli();
            // 查询当前时间的所有任务
            Set<String> data = jedis.zrangeByScore(key, lastSecond, nowSecond);
            for (String item : data) {
                System.out.printf("%s执行任务%s,执行时间:%s", Thread.currentThread().getName(), item, nowSecond);
                System.out.println();
            }
            // 删除已经执行的任务
            jedis.zremrangeByScore(key, lastSecond, nowSecond);
            final Long aLong = jedis.zcount(key, "0", Long.MAX_VALUE + "");
            if (aLong <= 0) {
                jedis.disconnect();
                jedis.close();
                break;
            }
            try {
                TimeUnit.SECONDS.sleep(1L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}