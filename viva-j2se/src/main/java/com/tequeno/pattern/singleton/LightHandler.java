package com.tequeno.pattern.singleton;

public class LightHandler {

//    private final static Light INSTANCE = new Light();
//
//    /**
//     * 方案一
//     * 需要先声明一个静态对象
//     * 可以防止多线程数据共享问题
//     * 但 INSTANCE 未被使用而先创建
//     *
//     * @return
//     */
//    @Deprecated
//    public static Light getInstance1() {
//        System.out.println("getInstance1");
//        return INSTANCE;
//    }


    private static Light s;

    /**
     * 方案二
     * 先声明一个空对象
     * 存在线程共享数据出错问题
     *
     * @return
     */
    @Deprecated
    public static Light getInstance2() {
        if (s == null) {
            System.out.println("getInstance2");
            s = new Light();
        }
        return s;
    }

    /**
     * 方案三
     * 在方案二基础上加上同步锁
     *
     * @return
     */
    @Deprecated
    public static synchronized Light getInstance3() {
        if (s == null) {
            System.out.println("getInstance3");
            s = new Light();
        }
        return s;
    }

    /**
     * 方案四 同方案三
     *
     * @return
     */
    @Deprecated
    public static Light getInstance4() {
        synchronized (Light.class) {
            if (s == null) {
                System.out.println("getInstance4");
                s = new Light();
            }
        }
        return s;
    }

    /**
     * 方案五
     * 使用双重校验锁(DCL)解决多线程下同步锁耗资源问题
     * 实际测试当线程量过多时并不一定能防止并发(几率很小)
     *
     * @return
     */
    public static Light getInstance5() {
        // 加一层判断可以减少同步锁的获取和释放占用的资源
        // 只要有一个线程完成一次加锁释放锁的操作，往后的线程就不再需要同步锁 了
        if (s == null) {
            System.out.println("getInstance5");
            synchronized (Light.class) {
                if (s == null) {
                    System.out.println("getInstance5-DCL");
                    s = new Light();
                }
            }
        }
        return s;
    }

    /**
     * 方案六
     * 懒汉式 推荐使用
     * 静态内部类延迟加载 优化饿汉式
     * 可以防止多线程数据共享问题
     *
     * @return
     */
    public static Light getInstance6() {
        return LightInnerHandler.INSTANCE;
    }

    private static class LightInnerHandler {
        private static final Light INSTANCE = new Light();
    }
}