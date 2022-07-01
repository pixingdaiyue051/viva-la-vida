package com.tequeno.pattern.singleton;

import java.util.concurrent.*;
import java.util.stream.IntStream;

/**
 * 可以适应懒加载模式
 */
public class SingletonTest {

    public static void main(String[] args) {
        ExecutorService pool = Executors.newCachedThreadPool();

        int size = 1000;
        CopyOnWriteArraySet<Singleton> set = new CopyOnWriteArraySet<>();

        CountDownLatch cd = new CountDownLatch(size);

        IntStream.range(0, size).forEach(i -> pool.execute(() -> {
            set.add(Singleton.getInstance5());
            cd.countDown();
        }));

        try {
            boolean await = cd.await(3L, TimeUnit.SECONDS);
            if (await) {
                System.out.println("set size ---" + set.size());
            } else {
                System.out.println("等到超时");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            pool.shutdown();
        }
    }
}