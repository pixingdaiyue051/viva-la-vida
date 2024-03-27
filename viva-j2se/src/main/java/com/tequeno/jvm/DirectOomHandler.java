package com.tequeno.jvm;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * 直接内存溢出测试
 * <p>
 * -XX:MaxDirectMemorySize=6m 直接内存大小
 * 如果不设置就和Xmx一致
 * <p>
 * 一般是使用到nio相关api时导致
 * Unsafe类api
 */
public class DirectOomHandler {

    /**
     * VM args: -Xmx10m -XX:MaxDirectMemorySize=6m -XX:+HeapDumpOnOutOfMemoryError
     * @throws IllegalAccessException
     */
    public void direct() throws IllegalAccessException {
        long size = 1024 * 1024L;
        Field f = Unsafe.class.getDeclaredFields()[0];
        f.setAccessible(true);
        Unsafe s = (Unsafe) f.get(null);
        while (true) {
            s.allocateMemory(size);
        }
    }
}