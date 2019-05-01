package com.tequeno.thread;

public class MyThread extends Thread {

    private Ticket ticket;

    public MyThread() {
        super();
    }

    public MyThread(Ticket ticket) {
        this.ticket = ticket;
    }

    @Override
    public void run() {
        try {
            ticket.operateNum(currentThread());
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println(currentThread().getName() + "--中断");
        }
    }

}
