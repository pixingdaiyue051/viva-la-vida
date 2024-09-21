package com.tequeno.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.*;

public class ThreadPoolHandler {

    private final static Logger log = LoggerFactory.getLogger(ThreadPoolHandler.class);

    /**
     * 使用future获得异步任务的返回值
     * future.get方法阻塞获得返回值
     * 按照future的添加顺序  顺序阻塞返回
     */
    public void cachedThread() {
        ExecutorService pool = Executors.newCachedThreadPool();

        ArrayList<Future<String>> futureArrayList = new ArrayList<>();
        System.out.println("main thread is running...");

        Future<String> future9 = pool.submit(() -> {
            System.out.println("future9 is running...");
            TimeUnit.SECONDS.sleep(9L);
            System.out.println("future9 over");
            return "future9 over";
        });
        futureArrayList.add(future9);

        Future<String> future3 = pool.submit(() -> {
            System.out.println("future3 is running...");
            TimeUnit.SECONDS.sleep(3L);
            System.out.println("future3 over");
            return "future3 over";
        });
        futureArrayList.add(future3);

        Future<String> future6 = pool.submit(() -> {
            System.out.println("future6 is running...");
            TimeUnit.SECONDS.sleep(6L);
            System.out.println("future6 over");
            return "future6 over";
        });
        futureArrayList.add(future6);

        try {
//            TimeUnit.SECONDS.sleep(1);
            // future.get()是阻塞式获取当前线程的返回值
            // 所以一定会优先获得list中第一个任务的结果
            for (Future<String> future : futureArrayList) {
                String returnStr = future.get();
                System.out.println(returnStr + " back ");
            }
//            Thread.currentThread().join();
        } catch (Exception e) {
            log.error("异常", e);
        } finally {
            pool.shutdown();
        }
    }

    /**
     * ExecutorCompletionService
     * 对future阻塞优化
     * 哪个future任务先结束就先获得返回值
     */
    public void completionService() {
        ExecutorService pool = Executors.newCachedThreadPool();
        ExecutorCompletionService<String> completionService = new ExecutorCompletionService<>(pool);
        System.out.println("main thread is running...");

        completionService.submit(() -> {
            System.out.println("future9 is running...");
            TimeUnit.SECONDS.sleep(9L);
            System.out.println("future9 over");
            return "future9 over";
        });

        completionService.submit(() -> {
            System.out.println("future3 is running...");
            TimeUnit.SECONDS.sleep(3L);
            System.out.println("future3 over");
            return "future3 over";
        });

        completionService.submit(() -> {
            System.out.println("future6 is running...");
            TimeUnit.SECONDS.sleep(6L);
            System.out.println("future6 over");
            return "future6 over";
        });

        try {
//            TimeUnit.SECONDS.sleep(1);
            // take获取到的还是future 因此仍是阻塞式的获取返回值
            // completionService会将最先执行完的任务结果返回
            for (int i = 0; i < 3; i++) {
                String returnStr = completionService.take().get();
                System.out.println(returnStr + " back ");
            }
//        Thread.currentThread().join();
        } catch (Exception e) {
            log.error("异常", e);
        } finally {
            pool.shutdown();
        }
    }

    /**
     * CountDownLatch
     * 计算异步任务结束  等待所有任务结束
     */
    public void countDown() {
        ExecutorService pool = Executors.newCachedThreadPool();

        System.out.println("main thread is running...");
        CountDownLatch cd = new CountDownLatch(3);

        pool.execute(() -> {
            System.out.println("future9 is running...");
            try {
                TimeUnit.SECONDS.sleep(9L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("future9 over");
            cd.countDown();
        });

        pool.execute(() -> {
            System.out.println("future3 is running...");
            try {
                TimeUnit.SECONDS.sleep(3L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("future3 over");
            cd.countDown();
        });

        pool.execute(() -> {
            System.out.println("future6 is running...");
            try {
                TimeUnit.SECONDS.sleep(6L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("future6 over");
            cd.countDown();
        });

        try {
            boolean b = cd.await(3L, TimeUnit.SECONDS);
            if (b) {
                System.out.println("所有任务正常运行结束");
            } else {
                System.out.println("等待超时");
            }
        } catch (Exception e) {
            log.error("异常", e);
        } finally {
            pool.shutdown();
        }
    }

    /**
     * awaitTermination
     * 只有调用了shutdown shutdownNow 让任务队列不再接收新的任务才能等待结束然后进行相关的操作
     */
    public void await() {
        ExecutorService pool = Executors.newCachedThreadPool();

        System.out.println("main thread is running...");

        pool.execute(() -> {
            System.out.println("future9 is running...");
            try {
                TimeUnit.SECONDS.sleep(9L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("future9 over");
        });

        try {
            pool.shutdown();
            boolean termination = pool.awaitTermination(10L, TimeUnit.SECONDS);
            if (termination) {
                System.out.println("线程池已关闭");
            } else {
                System.out.println("等待超时");
            }
        } catch (Exception e) {
            log.error("异常", e);
        }
    }

    public void scheduledThread() {
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