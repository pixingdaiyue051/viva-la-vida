package com.tequeno.thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 加锁解锁 流程
 */
public class Ticket {
    private int num = 0;

    private final Lock lock = new ReentrantLock();

    private void addNum1() {
        lock.lock();
        Thread thread = Thread.currentThread();
        System.out.println(thread.getName() + "--获得了锁");
        try {
            while (++num < 50) {
                System.out.println(thread.getName() + "--" + num);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("num最终值:" + num);
            System.out.println(thread.getName() + "--释放了锁");
            lock.unlock();
        }

    }

    private void addNum() {
        Thread thread = Thread.currentThread();
        synchronized (lock) {
            System.out.println(thread.getName() + "--获得了锁");
            while (++num < 50) {
                System.out.println(thread.getName() + "--" + num);
            }
            System.out.println("num最终值:" + num);
            System.out.println(thread.getName() + "--释放了锁");
        }
    }

    private void subNum1() {
        lock.lock();
        Thread thread = Thread.currentThread();
        System.out.println(thread.getName() + "--获得了锁");
        try {
            while (--num > 0) {
                System.out.println(thread.getName() + "--" + num);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("num最终值:" + num);
            System.out.println(thread.getName() + "--释放了锁");
            lock.unlock();
        }

    }

    private void subNum() {
        Thread thread = Thread.currentThread();
        synchronized (lock) {
            System.out.println(thread.getName() + "--获得了锁");
            while (--num > 0) {
                System.out.println(thread.getName() + "--" + num);
            }
            System.out.println("num最终值:" + num);
            System.out.println(thread.getName() + "--释放了锁");
        }
    }

    private void operateNum() throws InterruptedException {
        lock.lockInterruptibly();
        Thread thread = Thread.currentThread();
        System.out.println(thread.getName() + "--获得了锁");
        try {
            while (++num < 10000) {
                System.out.println(thread.getName() + "--" + num);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("num最终值:" + num);
            System.out.println(thread.getName() + "--释放了锁");
            lock.unlock();
        }
    }

    public void test() {

//        Thread t1 = new Thread(this::addNum1);
//        t1.setName("thread-test-t1");
//        t1.start();
//
//        Thread t3 = new Thread(this::addNum1);
//        t3.setName("thread-test-t3");
//        t3.start();

        Thread t2 = new Thread(() -> {
            try {
                this.operateNum();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t2.setName("thread-test-t2");
        t2.start();

        Thread t4 = new Thread(() -> {
            try {
                this.operateNum();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t4.setName("thread-test-t4");
        t4.start();

//        try {
//            TimeUnit.MILLISECONDS.sleep(10L);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        t2.interrupt();
    }
}