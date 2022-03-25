package com.tequeno.pattern.singleton;

/**
 * 可以适应懒加载模式
 */
public class Singletonplus {

    private static Singletonplus s = null;

    private Singletonplus() {

    }

    // 存在线程共享数据出错问题
    public static Singletonplus getInstance() {
        if (s == null) {
            s = new Singletonplus();
        }
        return s;
    }

    // 多线程下可以并发访问但是效率低下
    public static synchronized Singletonplus getInstance1() {
        if (s == null) {
            s = new Singletonplus();
        }
        return s;
    }

    // 和上面的方法一样有问题
    public static Singletonplus getInstance2() {
        synchronized (Singletonplus.class) {
            if (s == null) {
                s = new Singletonplus();
            }
        }
        return s;
    }

    // 解决多线程下同步锁耗资源问题
    public static Singletonplus getInstance3() {
        // 加一层判断可以减少同步锁的获取和释放占用的资源
        // 只要有一个线程完成一次加锁释放锁的操作，往后的线程就不再需要同步锁 了
        if (s == null) {
            synchronized (Singletonplus.class) {
                if (s == null) {
                    s = new Singletonplus();
                }
            }
        }
        return s;
    }
}