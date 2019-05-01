package com.tequeno.thread1;

public class Bank {

    private int sum;

    public synchronized void add(int num) {
        sum += num;
        try {
            Thread.sleep(20L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "...sum=" + sum);
    }
}
