package com.tequeno.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 多线程 隔离 交错打印字母和数字
 */
public class SharedDataModel {
    private final Lock lock = new ReentrantLock();
    private final Condition cNum = lock.newCondition();
    private final Condition cBet = lock.newCondition();
    private final int[] NUM = {1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
    private final int MAX_NUM_IDX = NUM.length - 1;
    private final char[] ALPHABET = {
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
    };
    private final int MAX_ALPHABET_IDX = ALPHABET.length - 1;

    private int iNum = 0;
    private int iBet = 0;

    /**
     * 控制打印数字线程的运行状态
     * false-----表示线程正在运行
     * true------表示线程已经挂起，等待唤醒
     */
    private boolean numThreadIsWaiting = false;

    /**
     * 控制打印字母线程的运行状态
     * false-----表示线程正在运行
     * true------表示线程已经挂起，等待唤醒
     */
    private boolean betThreadIsWaiting = true;

    private void printNum0() {
        System.out.print(Thread.currentThread().getName() + " print num to console:");
        System.out.println(NUM[iNum]);
        iNum = iNum == MAX_NUM_IDX ? 0 : iNum + 1;
        betThreadIsWaiting = false;// 准备唤醒字母线程
        numThreadIsWaiting = true;// 即将挂起数字线程
    }

    private void printBet0() {
        System.out.print(Thread.currentThread().getName() + " print bet to console:");
        System.out.println(ALPHABET[iBet]);
        iBet = iBet == MAX_ALPHABET_IDX ? 0 : iBet + 1;
        numThreadIsWaiting = false;// 准备唤醒数字线程
        betThreadIsWaiting = true;// 即将挂起字母线程
    }

    public void test() {

        int threadSize = 5;

        // 打印数字的线程
        for (int i = 0; i < threadSize; i++) {
            final Thread tn = new Thread(() -> {
                while (true) {
                    synchronized (lock) {
                        while (numThreadIsWaiting) {
                            try {
                                lock.wait();// 挂起当前线程，进入等待状态
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        printNum0();
                        lock.notifyAll();// 唤醒最早挂起的线程
                    }
                }
            });
            tn.setName("thread-num-" + i);
            tn.start();
        }

        // 打印字母的线程
        for (int i = 0; i < threadSize; i++) {
            final Thread tb = new Thread(() -> {
                while (true) {
                    synchronized (lock) {
                        while (betThreadIsWaiting) {
                            try {
                                lock.wait();// 挂起当前线程，进入等待状态
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        printBet0();
                        lock.notifyAll();// 唤醒最早挂起的线程
                    }
                }
            });
            tb.setName("thread-bet-" + i);
            tb.start();
        }
    }

    public void test1() {

        int threadSize = 5;

        // 打印数字的线程
        for (int i = 0; i < threadSize; i++) {
            final Thread tn = new Thread(() -> {
                while (true) {
                    lock.lock();
                    try {
                        while (numThreadIsWaiting) {
                            cNum.await();
                        }
                        printNum0();
                        cBet.signal();// 唤醒最早挂起的线程
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        lock.unlock();
                    }
                }
            });
            tn.setName("thread-num-" + i);
            tn.start();
        }

        // 打印字母的线程
        for (int i = 0; i < threadSize; i++) {
            final Thread tb = new Thread(() -> {
                while (true) {
                    lock.lock();
                    try {
                        while (betThreadIsWaiting) {
                            cBet.await();
                        }
                        printBet0();
                        cNum.signal();// 唤醒最早挂起的线程
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        lock.unlock();
                    }
                }
            });
            tb.setName("thread-bet-" + i);
            tb.start();
        }
    }
}