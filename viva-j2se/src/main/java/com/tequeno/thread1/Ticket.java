package com.tequeno.thread1;

public class Ticket extends Thread {

	private int num = 100;

	@Override
	public void run() {
		while(num-->0) {
			System.out.println(Thread.currentThread().getName() + "**sale**" + num);
		}
//		do {
//			System.out.println(super.getName() + "--sale--" + num--);
//		} while (num > 0);
	}

}