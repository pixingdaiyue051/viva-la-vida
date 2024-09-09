package com.tequeno.async;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class LockHandler {

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


    private final Object locka = new Object();
    private final Object lockb = new Object();

    private void run1() {
        synchronized (locka) {
            System.out.println("run1 locka");
            try {
                // 模拟再获取lockb之前的业务操作
                TimeUnit.SECONDS.sleep(1L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lockb) {
                System.out.println("run1 lockb");
            }
        }
    }

    private void run2() {
        synchronized (lockb) {
            System.out.println("run2 lockb");
            try {
                // 模拟再获取locka之前的业务操作
                TimeUnit.SECONDS.sleep(1L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (locka) {
                System.out.println("run2 locka");
            }
        }
    }

    /**
     * 线程A持有 locka
     * 线程B持有 lockb
     * 线程A再没有释放 locka 时就尝试去获取 lockb
     * 线程B再没有释放 lockb 时就尝试去获取 locka
     * 或者同时抢占对方持有的锁引起死锁
     */
    public void deadLock() {
        final Thread t1 = new Thread(this::run1);
        t1.setName("thread-dead-lock-t1");
        t1.start();
        final Thread t2 = new Thread(this::run2);
        t2.setName("thread-dead-lock-t2");
        t2.start();
    }
}