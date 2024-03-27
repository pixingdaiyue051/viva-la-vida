package com.tequeno.jdk8.function;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.*;

public class PredicateHandler {

    public <T> void filterList(List<T> tList, CommonPredicate<T> p) {
        Iterator<T> iterator = tList.iterator();
        while (iterator.hasNext()) {
            T t = iterator.next();
            if (!p.process(t)) {
                iterator.remove();
            }
        }
        tList.forEach(System.out::println);
    }

    /**
     * 测试----使用Predicate接口定义test方法
     * 该方法接收一个参数Object，返回boolean
     * T->boolean
     *
     * @param t
     * @param p
     * @param <T>
     * @return
     */
    public <T> void filterPredicate(T t, Predicate<T> p) {
        System.out.println(p.negate().test(t));
        System.out.println(p.test(t));
    }

    /**
     * 测试----使用Consumer接口定义accept方法
     * 该方法接收一个参数Object，无返回
     * T->void
     *
     * @param t
     * @param c
     * @param <T>
     */
    public <T> void filterConsumer(T t, Consumer<T> c) {
        c.accept(t);
    }

    /**
     * 测试----使用Function接口定义apply方法
     * 该方法接收一个参数Object，返回Object
     * 用于将输入映射到输出
     * T->R
     *
     * @param t
     * @param f
     * @param <T>
     * @param <R>
     * @return
     */
    public <T, R> void filterFunction(T t, Function<T, R> f) {
        System.out.println(f.apply(t));
    }

    /**
     * 测试----使用Supplier接口定义get方法
     * 测试----使用Callable接口定义call方法
     * 该方法无参，返回Object
     * ()->T
     *
     * @param s
     * @param <T>
     */
    public <T, V> void filterSupplier(Supplier<T> s, Callable<V> c) {
        try {
            System.out.println(s.get());
            System.out.println(c.call());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试----使用IntBinaryOperator接口定义applyAsInt方法
     * (int,int)->int
     *
     * @param ibo
     * @param left
     * @param right
     */
    public void filterIntBinaryOperator(int left, int right, IntBinaryOperator ibo) {
        System.out.println(left + "+" + right + "=" + ibo.applyAsInt(left, right));
    }

    /**
     * 测试----使用BiFunction接口定义apply方法
     * (T,U)->R
     *
     * @param t
     * @param u
     * @param bf
     * @param <T>
     * @param <U>
     * @param <R>
     */
    public <T, U, R> void filterBiFunction(T t, U u, BiFunction<T, U, R> bf) {
        System.out.println(bf.apply(t, u));
    }

    public <T> void filterComparator(T t1, T t2, Comparator<T> c1) {
        System.out.println(c1.compare(t2, t1));
    }

}
