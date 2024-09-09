package com.tequeno.async;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * t1 t2 main 按顺序先后执行完毕
 */
public class StopThreadHandler {

    /**
     * 不推荐
     */
    public void sleepTest() {

        Thread t1 = new Thread(() -> {
            System.out.println("t1----"); // t1立刻执行
        });
        Thread t2 = new Thread(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(500L); // t2休眠500ms
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("t2----");
        });

        t1.start();
        t2.start();
        try {
            TimeUnit.SECONDS.sleep(1L); // 主线休眠1s
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("main----");
    }

    /**
     * 不推荐
     */
    public void waitTest() {

        final Object lock = new Object();

        Thread t2 = new Thread(() -> {
            synchronized (lock) {
                try {
                    lock.wait(); // t2进入等待状态 当interrupt方法被触发时 线程被唤醒 程序会进入到catch代码块中
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("t2----");
                lock.notify(); // 最后唤醒main
            }
        });

        Thread t1 = new Thread(() -> {
            synchronized (lock) {
                try {
                    lock.wait(); // t1进入等待状态 当interrupt方法被触发时 线程被唤醒 程序会进入到catch代码块中
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("t1----");
                t2.interrupt(); // 最后唤醒t2
            }
        });
        t1.start();
        t2.start();


        t1.interrupt(); // 主线程中唤醒t1

        synchronized (lock) {
            try {
                lock.wait(); // main进入等待状态 当其他线程调用了notify时被唤醒 程序不会进入catch代码块中
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("main----");
        }
    }

    /**
     * TODO
     */
    public void signalTest() {

        final ReentrantLock lock = new ReentrantLock();
        final Condition condition = lock.newCondition();


        Thread t2 = new Thread(() -> {
            lock.lock();
            try {
                condition.await();
                System.out.println("t2----");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                condition.signal();
                lock.unlock();
            }
        });

        Thread t1 = new Thread(() -> {
            lock.lock();
            try {
                condition.await();
                System.out.println("t1----");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                condition.signal();
                lock.unlock();
            }
        });
        t1.start();
        t2.start();

        condition.signal();

        lock.lock();
        try {
            condition.await(); // 主线程进入等待
            System.out.println("main----");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 推荐
     */
    public void joinTest() {

        Thread t1 = new Thread(() -> System.out.println("t1----"));
        Thread t2 = new Thread(() -> {
            try {
                t1.join(); // 当前线程t2会等到t1执行完再执行
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("t2----");
        });

        t1.start();
        t2.start();
        try {
            t2.join(); // 主线程会等t2执行完再执行
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("main----");
    }
}
