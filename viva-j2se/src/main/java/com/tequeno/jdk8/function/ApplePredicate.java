package com.tequeno.jdk8.function;

/**
 * 测试使用谓词
 */
public interface ApplePredicate {
    /**
     * 比较方法，有具体的实现类决定用途
     *
     * @param apple
     * @return
     */
    boolean compare(Apple apple);
}
