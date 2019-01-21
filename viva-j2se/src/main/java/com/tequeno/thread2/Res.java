package com.tequeno.thread2;

public class Res {
	private String name;
	private String sex;
	private boolean flag = false;

	public synchronized void set(String name, String sex) {
		if (flag) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		this.name = name;
		this.sex = sex;
		this.flag = true;
		this.notify();
	}

	public synchronized void out() {
		if (!flag) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
		System.out.println(this.name + "****" + this.sex);
		this.flag = false;
		this.notify();
	}
}

// // 方案2
class Res2 {
	public String name;
	public String sex;
	public boolean flag = false;
}
