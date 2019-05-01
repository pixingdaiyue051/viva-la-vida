package com.tequeno.thread2;

public class StopThread implements Runnable {

    private boolean flag = true;

    @Override
    public synchronized void run() {
        while (flag) {
            try {
                wait();
            } catch (InterruptedException e) {
                flag = false;
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "....run");
        }
    }

    public void changFlag() {
        flag = flag ? false : true;
    }
}
