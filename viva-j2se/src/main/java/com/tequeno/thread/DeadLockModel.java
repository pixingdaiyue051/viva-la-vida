package com.tequeno.thread;

import java.util.concurrent.TimeUnit;

/**
 * 线程A持有 locka
 * 线程B持有 lockb
 * 线程A再没有释放 locka 时就尝试去获取 lockb
 * 线程B再没有释放 lockb 时就尝试去获取 locka
 * 或者同时抢占对方持有的锁引起死锁
 */
public class DeadLockModel {

    private final Object locka = new Object();
    private final Object lockb = new Object();

    public void run1() {
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

    public void run2() {
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

    public void test() {
        final Thread t1 = new Thread(this::run1);
        t1.setName("thread-dead-lock-t1");
        t1.start();
        final Thread t2 = new Thread(this::run2);
        t2.setName("thread-dead-lock-t2");
        t2.start();
    }
}
