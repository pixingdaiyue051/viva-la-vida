package com.tequeno.classload;

public class Book {
    private static int amount;

    private String str;

    static {
        System.out.println("静态代码块");
    }

    {
        System.out.println("普通代码块,amount=" + amount);
    }

    public Book() {
        System.out.println("构造方法,amount=" + amount);
    }

    public static void staticFunction() {
        System.out.println("静态方法,amount=" + amount);
    }

    public void casualFunction() {
        System.out.println("普通方法,amount=" + amount);
    }
}