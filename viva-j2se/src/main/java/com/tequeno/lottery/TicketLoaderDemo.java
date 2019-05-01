package com.tequeno.lottery;

public class TicketLoaderDemo {
    public static void main(String[] args) {
        LotteryTicket lt = new LotteryTicket();

        TicketLoader tl = new TicketLoader(lt);

        Thread t1 = new Thread(tl, "ticket-1");
        Thread t2 = new Thread(tl, "ticket-2");
        Thread t3 = new Thread(tl, "ticket-3");
        // Thread t4 = new Thread(tl, "ticket-4");
        // Thread t5 = new Thread(tl, "ticket-5");
        // t1.setDaemon(true);
        // t2.setDaemon(true);
        // t3.setDaemon(true);
        // t4.setDaemon(true);
        // t5.setDaemon(true);
        t1.start();
        t2.start();
        t3.start();
        // t4.start();
        // t5.start();

    }
}
