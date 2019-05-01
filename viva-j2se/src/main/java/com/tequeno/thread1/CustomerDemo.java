package com.tequeno.thread1;

public class CustomerDemo {

    public static void main(String[] args) {
        Customer cus = new Customer();
        Thread t1 = new Thread(cus);
        Thread t2 = new Thread(cus);
        Thread t3 = new Thread(cus);
        t1.start();
        t2.start();
        t3.start();
    }

}