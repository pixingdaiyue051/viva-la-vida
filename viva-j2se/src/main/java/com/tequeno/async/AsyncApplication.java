package com.tequeno.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class AsyncApplication {

    private final static Logger log = LoggerFactory.getLogger(AsyncApplication.class);

    public static void main(String[] args) {
        AsyncApplication a = new AsyncApplication();
//        a.delay();
//        a.stopThread();
//        a.map();
//        a.queue();
        a.pool();
    }

    private void delay() {
        DelayTaskHandler h = new DelayTaskHandler();
//        h.infiniteLoop();
//        h.delayedQueue();
//        h.timerTask();
        h.scheduledExecutor();
//        h.hashedWheelTimer();
    }

    public void stopThread() {
        StopThreadHandler h = new StopThreadHandler();
//        h.sleepTest();
//        h.waitTest();
        h.signalTest();
//        h.joinTest();
    }

    private void map() {
        Map<String, AtomicInteger> countMap = new HashMap<>();
        String str = "31";
        final int i = countMap.computeIfAbsent(str, s -> new AtomicInteger(0)).incrementAndGet();
        final int i1 = countMap.computeIfAbsent(str, s -> new AtomicInteger(0)).getAndIncrement();
        System.out.println(i);
        System.out.println(i1);
    }

    private void queue() {
        QueueHandler h = new QueueHandler();
        h.deque();
    }

    private void lock() {
//        TakeTurnHandler t = new TakeTurnHandler();
//        t.foobar(5);
//        t.alphaNum();
//
        LockHandler h = new LockHandler();
        h.repeatedACt();
    }

    private void pool() {
//        ThreadPoolHandler threadPoolHandler = new ThreadPoolHandler();
//        threadPoolHandler.cachedThread();

        ExecutorService pool = Executors.newCachedThreadPool();

//        AtomicInteger ai = new AtomicInteger(0);
//        AtomicDouble ad = new AtomicDouble(0.0D);
//        AtomicReference<BigDecimal> ab = new AtomicReference<>(BigDecimal.ZERO);
//        IntStream.range(0, 100).forEach(i -> pool.execute(() -> {
//            ai.incrementAndGet();
//            ad.addAndGet(i);
//            ab.updateAndGet(b -> b.add(BigDecimal.ONE));
//        }));

        AtomicReference<BigDecimal> amount = new AtomicReference<>(BigDecimal.ZERO);
        BigDecimal now = amount.get(); // 先获取改之前的值
        BigDecimal exp = now.add(BigDecimal.ONE); // 预计修改值
        ReentrantLock lock = new ReentrantLock();
        IntStream.range(0, 100).forEach(i -> pool.execute(() -> {
//            amount.compareAndSet(now, exp);

            boolean isLocked = lock.tryLock();
            if (!isLocked) {
                log.info("线程[{}]加锁失败", Thread.currentThread().getName());
                return;
            }
            try {
                log.info("线程[{}]获得锁", Thread.currentThread().getName());
                amount.updateAndGet(b -> exp);
            } finally {
                lock.unlock();
            }
        }));

        try {
            pool.shutdown();
            boolean termination = pool.awaitTermination(10, TimeUnit.SECONDS);
            if (termination) {
//                log.info("atomic {} {} {}", ai.get(), ad.get(), ab.get());
                log.info("{}", amount.get());
            }
        } catch (Exception e) {
            log.error("异常", e);
        }
    }
}
