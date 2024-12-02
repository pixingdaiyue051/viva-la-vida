package com.tequeno.async;

public class TakeTurnHandler {

    private final Object lock;
    private int turnSize;
    private boolean check;

    public TakeTurnHandler() {
        this.lock = new Object();
        this.check = true;
    }

    private void foo() {
        for (int i = 0; i < turnSize; i++) {
            synchronized (lock) {
                while (!check) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print("foo");
                check = false;
                lock.notify();
            }
        }
    }

    private void bar() {
        for (int i = 0; i < turnSize; i++) {
            synchronized (lock) {
                while (check) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("bar");
                check = true;
                lock.notify();
            }
        }
    }

    /**
     * 两个线程交替打印 foo bar 连成一个完整句子
     *
     * @param turnSize 循环次数
     */
    public void foobar(int turnSize) {
        this.turnSize = turnSize;
        new Thread(this::foo).start();
        new Thread(this::bar).start();
    }

    private void printAlpha(String alphaArr) {
        for (int i = 0; i < alphaArr.length(); i++) {
            synchronized (lock) {
                while (!check) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print(alphaArr.charAt(i));
                check = false;
                lock.notify();
            }
        }
    }

    private void printNumber(String numArr) {
        for (int i = 0; i < numArr.length(); i++) {
            synchronized (lock) {
                while (check) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print(numArr.charAt(i));
                check = true;
                lock.notify();
            }
        }
    }


    /**
     * 数字 字母交替打印
     */
    public void alphaNum() {
        String alphaArr = "jhgytfgv";
        String numArr = "764489087";
        new Thread(() -> this.printAlpha(alphaArr)).start();
        new Thread(() -> this.printNumber(numArr)).start();
    }
}