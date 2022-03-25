package com.tequeno.jdk8.ifunctional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.*;

import static java.util.Comparator.comparing;

public class PredicateTest {

    public static void main(String[] args) {

        List<String> list = new ArrayList<>();
        list.add("green");
        list.add("blue");
        list.add("red");
        list.add("gray");
        list.add("orange");
        list.add("purple");
        filterList(list, strInLambda -> "ok".equals(strInLambda) || strInLambda.length() > 4);

        filterPredicate("tequeno", strInLambda -> "ok".equals(strInLambda));

//        filterConsumer("ok", strInLambda -> System.out.println(strInLambda));
        filterConsumer("ok", System.out::println);

//        filterFunction("ok", strInLambda -> strInLambda.length());
        filterFunction("ok", String::length);

        filterSupplier(() -> "ok", () -> "tequeno".length());

        filterIntBinaryOperator(4, 5, (strInLeft, strInRight) -> strInLeft + strInRight);

        filterBiFunction("ok", "tequeno", (strInLambda1, strInLambda2) -> strInLambda1 + " " + strInLambda2);

//        filterComparator("ik", "ok", (lambdat1, lambdat2) -> lambdat1.length() - lambdat2.length());
        filterComparator("ok", "ok72fa", comparing(String::length));

        BiFunction<String, Double, Apple> f = Apple::new;
        List<Apple> appleList = new ArrayList();
        appleList.add(f.apply("1", 1d));
        appleList.add(f.apply("3", 3d));
        appleList.add(f.apply("2", 3d));
        appleList.add(f.apply("4", 4d));
        appleList.sort(comparing(Apple::getWeight).thenComparing(Apple::getColor));
        appleList.forEach((a) -> System.out.println(a.getColor() + "-" + a.getWeight()));
    }

    public static <T> void filterList(List<T> tList, CommonPredicate<T> p) {
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
    public static <T> void filterPredicate(T t, Predicate<T> p) {
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
    public static <T> void filterConsumer(T t, Consumer<T> c) {
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
    public static <T, R> void filterFunction(T t, Function<T, R> f) {
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
    public static <T, V> void filterSupplier(Supplier<T> s, Callable<V> c) {
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
    public static void filterIntBinaryOperator(int left, int right, IntBinaryOperator ibo) {
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
    public static <T, U, R> void filterBiFunction(T t, U u, BiFunction<T, U, R> bf) {
        System.out.println(bf.apply(t, u));
    }

    public static <T> void filterComparator(T t1, T t2, Comparator<T> c1) {
        System.out.println(c1.compare(t2, t1));
    }

}
