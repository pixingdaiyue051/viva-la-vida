package com.tequeno.thread2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Resource {
	private String name;
	private int count = 1;
	private boolean flag = false;

	private final Lock lock = new ReentrantLock();

	private final Condition condition_pro = lock.newCondition();
	
	private final Condition condition_con = lock.newCondition();

	public synchronized void set(String name) {
		while (flag) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		this.name = name + "**" + (count++);
		System.out.println(Thread.currentThread().getName() + "**produce**" + this.name);
		this.flag = true;
		this.notifyAll();
	}

	public synchronized void out() {
		while (!flag) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println(Thread.currentThread().getName() + "**consume**" + this.name);
		this.flag = false;
		this.notifyAll();
	}

	public void setHolder(String name) {
		lock.lock();
		try {
			while (flag) {
				condition_pro.await();
			}
			this.name = name + "**" + (count++);
			System.out.println(Thread.currentThread().getName() + "**produce**" + this.name);
			this.flag = true;
			condition_con.signal();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

	public void outHolder() {
		lock.lock();
		try {
			while (!flag) {
				condition_con.await();
			}
			System.out.println(Thread.currentThread().getName() + "**consume**" + this.name);
			this.flag = false;
			condition_pro.signal();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}
}