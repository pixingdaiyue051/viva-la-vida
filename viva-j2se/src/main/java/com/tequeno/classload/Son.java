package com.tequeno.classload;

public class Son extends Father {

    static {
        System.out.println("Son - 静态代码块");
    }

    {
        System.out.println("Son - 普通代码块");
    }

    public Son() {
        System.out.println("Son - 构造方法");
    }

    public void run() {
        System.out.println("Son - 方法");
    }

    public static void staticRun() {
        System.out.println("Son - 静态方法");
    }
}