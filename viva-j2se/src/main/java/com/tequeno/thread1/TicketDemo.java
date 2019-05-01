package com.tequeno.thread1;

public class TicketDemo {

    public static void main(String[] args) {
        implRun();
    }

    public static void threadRun() {
        Ticket t = new Ticket();
        t.start();
    }

    public static void implRun() {
        NewTicket target = new NewTicket();
        Thread t1 = new Thread(target);
        Thread t2 = new Thread(target);
        t1.start();
        try {
            Thread.sleep(200L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        target.flag = false;
        t2.start();
    }

}
