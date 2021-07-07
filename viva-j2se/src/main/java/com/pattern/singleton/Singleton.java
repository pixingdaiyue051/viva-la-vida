package com.pattern.singleton;

import java.util.Map;

/**
 * 适合非懒加载模式下
 */
public class Singleton {

//    private final static Singleton INSTANCE = new Singleton();

    {
        System.out.println("singleton blank area");
    }

    static {
        System.out.println("singleton static area");
    }

    private Singleton() {
        System.out.println("singleton constructor");
    }

//    public static Singleton getInstance() {
//        return INSTANCE;
//    }

    public static Singleton getInstance1() {
        Map<String, String> map = SingletonHolder.map;

        return null;
    }

    // 静态内部类解决懒加载问题
    private static class SingletonHolder {

        private final static Singleton INSTANCE = new Singleton();

        private final static Map<String, String> map = null;

        {
            System.out.println("blank area");
        }

        static {
            System.out.println("static area");
        }

        private SingletonHolder() {
            System.out.println("constructor");
        }
    }

    public static void main(String[] args) {
//        Singleton s =new Singleton();
        Singleton.getInstance1();
        Singleton.getInstance1();
    }
}