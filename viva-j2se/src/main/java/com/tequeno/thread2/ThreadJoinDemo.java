package com.tequeno.thread2;

public class ThreadJoinDemo {
	public static void main(String[] args) {
		ThreadJoin t = new ThreadJoin();

		Thread t1 = new Thread(t);
		Thread t2 = new Thread(t);

		t1.start();
		t2.start();
		try {
			// 主线程释放CPU执行权,此时t1 t2抢占执行权,等t1执行完之后,主线程才会回到可运行状态
			// 不用管t2是否执行完毕
			t1.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// yield() 暂停当前线程，并且执行其他线程
		
		for (int i = 0; i < 100; i++) {
			System.out.println(Thread.currentThread().getName() + "..." + i);
		}
	}
}

class ThreadJoin implements Runnable {

	@Override
	public void run() {
		for (int i = 0; i < 100; i++) {
			System.out.println(Thread.currentThread().getName() + "..." + i);
		}
	}

}