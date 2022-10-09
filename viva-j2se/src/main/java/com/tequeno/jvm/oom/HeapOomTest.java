package com.tequeno.jvm.oom;

import java.util.ArrayList;
import java.util.List;

/**
 * 堆内存溢出测试
 * VM args
 * -Xms20m 堆内存初始化size
 * -Xmx20m 最大可扩展到size  当Xmx和Xms相同时表示不可扩展
 * -XX:+HeapDumpOnOutOfMemoryError  出现oom时dump当前内存存储快照
 */
public class HeapOomTest {


    /**
     * VM args: -Xmx10m -Xms10m -XX:+HeapDumpOnOutOfMemoryError
     *
     * @param args
     */
    public static void main(String[] args) {

        List<OomObject> list = new ArrayList<>();

        while (true) {
            list.add(new OomObject());
        }
    }
}