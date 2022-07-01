package com.tequeno.threadpool;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledPoolService {

    // volatile保证数据在线程间可见
    private volatile long now;

    public void scheduleTick() {
        final long l1 = System.currentTimeMillis();
        now = l1;
//        ScheduledExecutorService service = Executors.newScheduledThreadPool(10, runnable -> {
//            Thread thread = new Thread(runnable, "current-time-millis-%d");
//            thread.setDaemon(true);
//            return thread;
//        });
        ScheduledExecutorService service = Executors.newScheduledThreadPool(10);

        service.execute(() -> {
            System.out.print(Thread.currentThread().getName() + "----");
            System.out.println(LocalDateTime.now().toString());
        });

        service.schedule(() -> {
            System.out.print(Thread.currentThread().getName() + "----");
            System.out.println(LocalDateTime.now().toString());
        }, 3, TimeUnit.SECONDS);
//
//        service.scheduleAtFixedRate(() -> {
//            now = System.currentTimeMillis();
//            System.out.print(Thread.currentThread().getName() + "----");
//            System.out.println(now - l1);
//        }, 1, 1, TimeUnit.SECONDS);
//
//        service.scheduleWithFixedDelay(() -> {
////            now = System.currentTimeMillis();
//            System.out.println(now - l1);
//        }, 2, 2, TimeUnit.SECONDS);

        service.schedule(service::shutdown, 30, TimeUnit.SECONDS);
    }

}