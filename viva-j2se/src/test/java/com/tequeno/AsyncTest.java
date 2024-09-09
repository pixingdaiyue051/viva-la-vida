package com.tequeno;

import com.tequeno.async.*;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class AsyncTest {

    @Test
    public void testDelay() {
        DelayTaskHandler test = new DelayTaskHandler();
//        test.infiniteLoop();
        test.delayedQueue();
//        test.timerTask();
//        test.scheduledExecutor();
//        test.hashedWheelTimer();
    }

    @Test
    public void testMap() {
        Map<String, AtomicInteger> countMap = new HashMap<>();
        String str = "31";
        final int i = countMap.computeIfAbsent(str, s -> new AtomicInteger(0)).incrementAndGet();
        final int i1 = countMap.computeIfAbsent(str, s -> new AtomicInteger(0)).getAndIncrement();
        System.out.println(i);
        System.out.println(i1);
    }

    @Test
    public void testQueue() {
        QueueHandler test = new QueueHandler();
        test.stack();
    }

    @Test
    public void testThread() {
        StopThreadHandler stopThreadHandler = new StopThreadHandler();
        stopThreadHandler.joinTest();
    }

    @Test
    public void testThreadPool() {
        ThreadPoolHandler threadPoolHandler = new ThreadPoolHandler();
        threadPoolHandler.cachedThread();
    }
}
