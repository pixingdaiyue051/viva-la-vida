package com.tequeno.thread2;

public class ResOutput implements Runnable {

	private Res r;

	public ResOutput(Res r) {
		this.r = r;
	}

	@Override
	public void run() {
		while (true) {
			r.out();
		}
	}

}

// // 方案2

class ResOutput2 implements Runnable {

	private Res2 r;

	public ResOutput2(Res2 r) {
		this.r = r;
	}

	@Override
	public void run() {
		while (true) {
			synchronized (r) {
				if (r.flag) {
					System.out.println(r.name + "****" + r.sex);
					r.flag = false;
					r.notify();
				} else {
					try {
						r.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

}
