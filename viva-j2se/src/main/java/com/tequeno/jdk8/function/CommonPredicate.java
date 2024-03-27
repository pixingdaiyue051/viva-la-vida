package com.tequeno.jdk8.function;

/**
 * 自定义函数接口，使用谓词
 *
 * @param <T>
 */
@FunctionalInterface
public interface CommonPredicate<T> {
    /**
     * 处理lambda请求的结果，只要形式为T->boolean的lambda
     *
     * @param t
     * @return
     */
    boolean process(T t);
}
