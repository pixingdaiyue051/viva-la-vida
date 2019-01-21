package com.tequeno.thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Ticket {
	private int num;

	private Lock lock;

	public Ticket() {
		num = 0;
		lock = new ReentrantLock();
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public void addNum1(Thread thread) {
		lock.lock();
		try {
			System.out.println(thread.getName() + "--获得了锁");
			while (++num < 50) {
				System.out.println(thread.getName() + "--" + num);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
			System.out.println(thread.getName() + "--释放了锁");
		}

	}

	public void addNum(Thread thread) {
		synchronized (this) {
			while (++num < 50) {
				System.out.println(thread.getName() + "--" + num);
			}
		}
	}

	public void subNum1(Thread thread) {
		lock.lock();
		try {
			System.out.println(thread.getName() + "--获得了锁");
			while (--num > 0) {
				System.out.println(thread.getName() + "--" + num);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
			System.out.println(thread.getName() + "--释放了锁");
		}

	}

	public void subNum(Thread thread) {
		synchronized (this) {
			while (--num > 0) {
				System.out.println(thread.getName() + "--" + num);
			}
		}
	}

	public void operateNum(Thread thread) throws InterruptedException {
		lock.lockInterruptibly();
		try {
			System.out.println(thread.getName() + "--获得了锁");
			while (++num < 10000) {
				System.out.println(thread.getName() + "--" + num);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
			System.out.println(thread.getName() + "--释放了锁");
		}

	}
}
