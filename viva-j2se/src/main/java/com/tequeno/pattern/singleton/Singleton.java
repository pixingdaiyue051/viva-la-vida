package com.tequeno.pattern.singleton;

/**
 * 适合非懒加载模式下
 */
public class Singleton {

    private final static Singleton INSTANCE = new Singleton();

    /**
     * 饿汉式 需要先声明一个静态对象
     * 可以防止多线程数据共享问题
     * 但 INSTANCE 未被使用而先创建
     *
     * @return
     */
    public static Singleton getInstance() {
        System.out.println("getInstance");
        return INSTANCE;
    }

    private static class SingletonHolder {

        private static final Singleton INSTANCE;

        static {
            System.out.println("SingletonHolder static");
            INSTANCE = new Singleton();
        }
    }

    /**
     * 静态内部类方式 优化饿汉式
     * 可以防止多线程数据共享问题
     * INSTANCE 延迟加载
     *
     * @return
     */
    public static Singleton getInstance1() {
        return SingletonHolder.INSTANCE;
    }


    private static Singleton s;

    /**
     * 懒汉式  先声明一个空对象
     * 存在线程共享数据出错问题
     *
     * @return
     */
    public static Singleton getInstance2() {
        if (s == null) {
            System.out.println("getInstance2");
            s = new Singleton();
        }
        return s;
    }

    /**
     * 懒汉式  先声明一个空对象
     * 可以防止多线程数据共享问题 但是效率低下
     * 实际测试当线程量过多时并不一定能防止并发(几率很小)
     *
     * @return
     */
    public static synchronized Singleton getInstance3() {
        if (s == null) {
            System.out.println("getInstance3");
            s = new Singleton();
        }
        return s;
    }

    /**
     * 和上面的方法一样有问题
     * 实际测试当线程量过多时并不一定能防止并发(几率很小)
     * @return
     */
    public static Singleton getInstance4() {
        synchronized (Singleton.class) {
            if (s == null) {
                System.out.println("getInstance4");
                s = new Singleton();
            }
        }
        return s;
    }

    /**
     * 懒汉式  先声明一个空对象
     * 使用双重校验锁(DCL)解决多线程下同步锁耗资源问题
     * 实际测试当线程量过多时并不一定能防止并发(几率很小)
     * @return
     */
    public static Singleton getInstance5() {
        // 加一层判断可以减少同步锁的获取和释放占用的资源
        // 只要有一个线程完成一次加锁释放锁的操作，往后的线程就不再需要同步锁 了
        if (s == null) {
            System.out.println("getInstance5");
            synchronized (Singleton.class) {
                if (s == null) {
                    System.out.println("getInstance5-DCL");
                    s = new Singleton();
                }
            }
        }
        return s;
    }
}