package com.tequeno.thread;

public class ThreadLocalTest {

    public static void main(String[] args) {

        final Ticket ticket = new Ticket();

        new Thread() {
            @Override
            public void run() {
                ticket.addNum1(currentThread());
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                ticket.subNum1(currentThread());
            }
        }.start();

        MyThread t1 = new MyThread(ticket);
        MyThread t2 = new MyThread(ticket);
        t1.start();
        t2.start();
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        t2.interrupt();
    }

}
