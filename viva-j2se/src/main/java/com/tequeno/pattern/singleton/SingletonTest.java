package com.tequeno.pattern.singleton;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * 可以适应懒加载模式
 */
public class SingletonTest {

    public static void main(String[] args) {
        ExecutorService pool = Executors.newCachedThreadPool();

        int size = 1000;
        Set<Singleton> set = new HashSet<>(size);

        CountDownLatch cd = new CountDownLatch(size);

        IntStream.range(0, size).forEach(i -> pool.execute(() -> {
            set.add(Singleton.getInstance1());
            cd.countDown();
        }));

        try {
            boolean await = cd.await(3L, TimeUnit.SECONDS);
            if (await) {
                System.out.println("set size ---" + set.size());
                pool.shutdown();
            } else {
                System.out.println("等到超时");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}