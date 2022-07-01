package com.tequeno.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class LockModel {

    final ReentrantLock lock = new ReentrantLock();
    final Condition condition = lock.newCondition();

    /**
     * 模拟多线程竞争进入同一个方法
     */
    public void multiAct() {
        final ExecutorService pool = Executors.newCachedThreadPool();
        IntStream.range(0, 10)
                .forEach(i -> pool.execute(this::mockAcquire));
        pool.shutdown();
    }

    private void mockAcquire() {
        if (!lock.tryLock()) {
            return;
        }
        try {
            // 模拟执行任务需要的时间
            System.out.println(Thread.currentThread().getName());
            TimeUnit.SECONDS.sleep(1L);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void act() {
        Thread t1 = new Thread(() -> {

        });
        t1.start();

        Thread t2 = new Thread(() -> {

        });
        t2.start();

        Thread t3 = new Thread(() -> {

        });
        t3.start();

    }

}