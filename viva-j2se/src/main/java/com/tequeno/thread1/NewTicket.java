package com.tequeno.thread1;

public class NewTicket implements Runnable {
	
	private int num = 1000;
	public boolean  flag = true;
	private Object o = new Object();
	@Override
	public void run() {
		if(flag) {
			while(true) {
				synchronized (o){
					if(num>0) {
						try {
							Thread.sleep(10L);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						System.out.println(Thread.currentThread().getName() + "**code**" + num--);
					}
				}
			}
		}else {
			while(true) {
				show();
			}
		}
		
	}
	
	public synchronized void show() {
		if(num>0) {
			try {
				Thread.sleep(10L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName() + "**func**" + num--);
		}
	}
}

