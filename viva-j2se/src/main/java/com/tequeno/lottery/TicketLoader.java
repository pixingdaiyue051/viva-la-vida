package com.tequeno.lottery;

public class TicketLoader implements Runnable {

	private LotteryTicket lt;

	private int count;

	public TicketLoader() {
	}

	public TicketLoader(LotteryTicket lt) {
		this.lt = lt;
		count = 0;
	}

	@Override
	public void run() {
		while (count++ < 100) {
			System.out.println(Thread.currentThread().getName() + "***" + lt.generateTicket());
//			synchronized (lt) {
//			}
		}
	}
}
