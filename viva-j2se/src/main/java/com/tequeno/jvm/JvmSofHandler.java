package com.tequeno.jvm;

/**
 * 虚拟机栈溢出测试
 * Hotspot 将虚拟机栈和本地方法栈合二为一 且不支持栈的动态扩展
 * VM args:
 * -Xss256k 设置虚拟机栈的初始化内存大小
 * -Xoss256k 设置本地方法栈的初始化内存大小(对Hotspot没有意义)
 * <p>
 * 如果线程请求的栈深度大于虚拟机允许的深度将抛出 StackOverflowError
 * 如果扩展栈的内存大小超出初始化设置的栈内存大戏将抛出 OutOfMemoryError(对Hotspot不可能发生)
 */
public class JvmSofHandler {

    public long stackLength = 0;

    /**
     * 一个闭环不带自动退出的循环引用 以加深栈的调用深度
     * 模拟线程请求栈帧深度超出虚拟机允许最大深度
     */
    public void stackLengthSof() {
        stackLength++;
        stackLengthSof();
    }

    /**
     * 方法调用内部定义大量无用变量 不断消耗栈内存
     * 模拟栈内存溢出
     */
    public void stackSizeSof() {
        double d1,
                d2,
                d3,
                d4,
                d5,
                d6,
                d7,
                d8,
                d9,
                d10,
                d11,
                d12,
                d13,
                d14,
                d15,
                d16,
                d17,
                d18,
                d19,
                d20,
                d21,
                d22,
                d23,
                d24,
                d25,
                d26,
                d27,
                d28,
                d29,
                d30,
                d31,
                d32,
                d33,
                d34,
                d35,
                d36,
                d37,
                d38,
                d39,
                d40,
                d41,
                d42,
                d43,
                d44,
                d45,
                d46,
                d47,
                d48,
                d49,
                d50,
                d51,
                d52,
                d53,
                d54,
                d55,
                d56,
                d57,
                d58,
                d59,
                d60,
                d61,
                d62,
                d63,
                d64,
                d65,
                d66,
                d67,
                d68,
                d69,
                d70,
                d71,
                d72,
                d73,
                d74,
                d75,
                d76,
                d77,
                d78,
                d79,
                d80,
                d81,
                d82,
                d83,
                d84,
                d85,
                d86,
                d87,
                d88,
                d89,
                d90,
                d91,
                d92,
                d93,
                d94,
                d95,
                d96,
                d97,
                d98,
                d99,
                d100;
        stackLength++;
        stackSizeSof();
        d1 = 0;
        d2 = 0;
        d3 = 0;
        d4 = 0;
        d5 = 0;
        d6 = 0;
        d7 = 0;
        d8 = 0;
        d9 = 0;
        d10 = 0;
        d11 = 0;
        d12 = 0;
        d13 = 0;
        d14 = 0;
        d15 = 0;
        d16 = 0;
        d17 = 0;
        d18 = 0;
        d19 = 0;
        d20 = 0;
        d21 = 0;
        d22 = 0;
        d23 = 0;
        d24 = 0;
        d25 = 0;
        d26 = 0;
        d27 = 0;
        d28 = 0;
        d29 = 0;
        d30 = 0;
        d31 = 0;
        d32 = 0;
        d33 = 0;
        d34 = 0;
        d35 = 0;
        d36 = 0;
        d37 = 0;
        d38 = 0;
        d39 = 0;
        d40 = 0;
        d41 = 0;
        d42 = 0;
        d43 = 0;
        d44 = 0;
        d45 = 0;
        d46 = 0;
        d47 = 0;
        d48 = 0;
        d49 = 0;
        d50 = 0;
        d51 = 0;
        d52 = 0;
        d53 = 0;
        d54 = 0;
        d55 = 0;
        d56 = 0;
        d57 = 0;
        d58 = 0;
        d59 = 0;
        d60 = 0;
        d61 = 0;
        d62 = 0;
        d63 = 0;
        d64 = 0;
        d65 = 0;
        d66 = 0;
        d67 = 0;
        d68 = 0;
        d69 = 0;
        d70 = 0;
        d71 = 0;
        d72 = 0;
        d73 = 0;
        d74 = 0;
        d75 = 0;
        d76 = 0;
        d77 = 0;
        d78 = 0;
        d79 = 0;
        d80 = 0;
        d81 = 0;
        d82 = 0;
        d83 = 0;
        d84 = 0;
        d85 = 0;
        d86 = 0;
        d87 = 0;
        d88 = 0;
        d89 = 0;
        d90 = 0;
        d91 = 0;
        d92 = 0;
        d93 = 0;
        d94 = 0;
        d95 = 0;
        d96 = 0;
        d97 = 0;
        d98 = 0;
        d99 = 0;
        d100 = 0;
    }

    /**
     * os为每个线程分配的内存时有限的(32bit windows 单个进程最大内存为2G)
     * 栈内存=进程内存大小-最大堆内存-最大方法去内存-最大直接内存-程序计数器内存-进程本身占用的内存(一般只考虑前两项的影响)
     * 为每个线程分配的内存越大，该进程内能创建的线程就越少
     * 由于windows平台的vm会把线程映射到os内核中，该方法会导致过高占用内存系统假死
     */
    @Deprecated
    public void stackLeakByThread() {
        while(true) {
            new Thread(() -> {while (true);}).start();
        }
    }
}