package com.tequeno.thread2;

public class ResDemo {
	public static void main(String[] args) {
		 Res r = new Res();
		 new Thread(new ResInput(r)).start();
		 new Thread(new ResOutput(r)).start();

//		Res2 r2 = new Res2();
//		new Thread(new ResInput2(r2)).start();
//		new Thread(new ResOutput2(r2)).start();

	}

}
