package com.tequeno.jdk8.utilcon;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Desription: jdk concurrent包测试类
 * @Author: hexk
 * @date : 2019-10-28 15:54
 */
public class ConTest {
    public static void main(String[] args) {
        Map<String, String> map = new ConcurrentHashMap<>();
        map.put("1", "s1");
        map.put("2", "s2");
        map.put("3", "s3");
        map.forEach((k, v) -> System.out.println(k + "---" + v));

        // jdk提供线程缓冲池
        ThreadPoolExecutor pool = new ScheduledThreadPoolExecutor(1);
        pool.execute(() -> System.out.println(Thread.currentThread().getName()));
        pool.shutdown();
        // jdk封装的线程缓冲池，对应有其他相关的参数
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(() -> System.out.println(Thread.currentThread().getName()));
        executorService.execute(() -> System.out.println(Thread.currentThread().getName()));
        executorService.execute(() -> System.out.println(Thread.currentThread().getName()));
        executorService.shutdown();
        // spring针对jdk的ThreadPoolExecutor封装的ThreadPoolTaskExecutor可以作为Executors替代
    }
}