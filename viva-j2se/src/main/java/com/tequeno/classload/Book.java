package com.tequeno.classload;

public class Book {
    public static Book book;
    private static int amount = 112;

    static {
        System.out.println("书的静态代码块");
//		System.out.println("书的静态代码块" + ",amount=" + amount);
        book = new Book();
    }

    {
        System.out.println("书的普通代码块" + ",amount=" + amount);
    }

    public Book() {
        System.out.println("书的构造方法" + ",amount=" + amount);
    }

    public static void main(String[] args) {
        staticFunction();
        book.casualFunction();
    }

    public static void staticFunction() {
        System.out.println("书的静态方法" + ",amount=" + amount);
    }

    public void casualFunction() {
        System.out.println("书的普通方法" + ",amount=" + amount);
    }
}