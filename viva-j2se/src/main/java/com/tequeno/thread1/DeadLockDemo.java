package com.tequeno.thread1;

public class DeadLockDemo {
    public static void main(String[] args) {
        DeadLock target1 = new DeadLock(true);
        DeadLock target2 = new DeadLock(false);
        Thread t1 = new Thread(target1);
        Thread t2 = new Thread(target2);
        t1.start();
        t2.start();
    }
}

class DeadLock implements Runnable {

    private boolean flag = true;

    public DeadLock(boolean flag) {
        this.flag = flag;
    }

    @Override
    public void run() {
        if (flag) {
            synchronized (MyLock.locka) {
                System.out.println("if locka");
                synchronized (MyLock.lockb) {
                    System.out.println("if lockb");
                }
            }
        } else {
            synchronized (MyLock.lockb) {
                System.out.println("else lockb");
                synchronized (MyLock.locka) {
                    System.out.println("if locka");
                }
            }
        }

    }

}

class MyLock {
    public static Object locka = new Object();
    public static Object lockb = new Object();
}