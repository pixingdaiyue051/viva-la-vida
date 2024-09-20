package com.tequeno.classload;

public class Grandpa {

    static {
        System.out.println("Grandpa - 静态代码块");
    }

    {
        System.out.println("Grandpa - 普通代码块");
    }

    public Grandpa() {
        System.out.println("Grandpa - 构造方法");
    }
}