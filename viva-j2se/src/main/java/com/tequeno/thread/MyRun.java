package com.tequeno.thread;

public class MyRun implements Runnable {

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "run");
    }

}
