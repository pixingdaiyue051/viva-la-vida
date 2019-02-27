package com.tequeno.thread.reqres;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SharedDataPlusDemo {
    private final static int NUM_THREAD_NUMS = 3;

    private final static int BET_THREAD_NUMS = 3;

    // 主线程
    public static void main(String[] args) {

        CommonSharedPlusData sharedData = new CommonSharedPlusData();

        // 打印数字的线程
        for (int i = 0; i < NUM_THREAD_NUMS; i++) {
            new Thread(() -> {
                while (true) {
                    sharedData.printNums();
                }
            }).start();
        }

        // 打印字母的线程
        for (int i = 0; i < BET_THREAD_NUMS; i++) {
            new Thread(() -> {
                while (true) {
                    sharedData.printBets();
                }
            }).start();
        }
    }
}

/**
 * 共享数据基础类
 */
class CommonSharedPlusData {
    private int[] nums = {1, 2, 3, 4, 5, 6, 7, 8, 9, 0};

    private char[] alphabets = {
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
    };

    final private int MAX_NUMS_INDEX = nums.length - 1;
    final private int MAX_BETS_INDEX = alphabets.length - 1;

    private int iNum = 0;
    private int iBet = 0;

    /**
     * 控制打印数字线程的运行状态
     * false-----表示线程正在运行
     * true------表示线程已经挂起，等待唤醒
     */
    private boolean bNum = false;

    /**
     * 控制打印字母线程的运行状态
     * false-----表示线程正在运行
     * true------表示线程已经挂起，等待唤醒
     */
    private boolean bBet = true;

    private final Lock lock = new ReentrantLock();

    private final Condition cNum = lock.newCondition();

    private final Condition cBet = lock.newCondition();

    public void printNums() {
        lock.lock();
        try {
            while (bNum) {
                cNum.await();
            }
            System.out.print(Thread.currentThread().getName() + " print num to console:");
            System.out.println(nums[iNum]);
//            System.out.print(nums[iNum]);
            iNum = iNum == MAX_NUMS_INDEX ? 0 : iNum + 1;
            bBet = false;// 准备唤醒字母线程
            bNum = true;// 即将挂起数字线程
            cBet.signalAll();// 唤醒最早挂起的线程
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void printBets() {
        lock.lock();
        try {
            while (bBet) {
                cBet.await();
            }
            System.out.print(Thread.currentThread().getName() + " print bet to console:");
            System.out.println(alphabets[iBet]);
//            System.out.print(alphabets[iBet]);
            iBet = iBet == MAX_BETS_INDEX ? 0 : iBet + 1;
            bNum = false;// 准备唤醒数字线程
            bBet = true;// 即将挂起字母线程
            cNum.signalAll();// 唤醒最早挂起的线程
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
