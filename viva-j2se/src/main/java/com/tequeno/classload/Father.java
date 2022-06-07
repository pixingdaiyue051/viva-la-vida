package com.tequeno.classload;

public class Father extends Grandpa {

    static {
        System.out.println("Father - 静态代码块");
    }

    {
        System.out.println("Father - 普通代码块");
    }

    public Father() {
        System.out.println("Father - 构造方法");
    }
}