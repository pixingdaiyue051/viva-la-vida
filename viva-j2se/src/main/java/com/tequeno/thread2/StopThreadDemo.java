package com.tequeno.thread2;

public class StopThreadDemo {
    public static void main(String[] args) {
        StopThread st = new StopThread();

        Thread t1 = new Thread(st);
        Thread t2 = new Thread(st);
//		t1.setDaemon(true);
//		t2.setDaemon(true);
        t1.start();
        t2.start();

        int num = 0;
        while (true) {
            if ((num++) > 60) {
                t1.interrupt();
                t2.interrupt();
                break;
            }
            System.out.println(Thread.currentThread().getName() + "...run");
        }
    }
}
