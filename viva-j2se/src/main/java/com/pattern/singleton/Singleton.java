package com.pattern.singleton;

/**
 * 适合非懒加载模式下
 */
public class Singleton {

    private final static Singleton INSTANCE = new Singleton();

    private Singleton() {

    }

    public static Singleton getInstance() {
        return INSTANCE;
    }

    public static Singleton getInstance1() {
        return SingletonHolder.INSTANCE;
    }

    // 静态内部类解决懒加载问题
    private static class SingletonHolder {
        private final static Singleton INSTANCE = new Singleton();
    }
}