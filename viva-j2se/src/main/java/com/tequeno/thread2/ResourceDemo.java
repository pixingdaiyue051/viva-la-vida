package com.tequeno.thread2;

public class ResourceDemo {
	public static void main(String[] args) {
		Resource r = new Resource();

		ResourceProducer rr = new ResourceProducer(r);
		ResourceConsumer rc = new ResourceConsumer(r);

		Thread t1 = new Thread(rr);
		t1.start();
		Thread t2 = new Thread(rr);
		t2.start();
		Thread t3 = new Thread(rr);
		t3.start();
		
		Thread t4 = new Thread(rc);
		t4.start();
		Thread t5 = new Thread(rc);
		t5.start();
		Thread t6 = new Thread(rc);
		t6.start();
	}

}
