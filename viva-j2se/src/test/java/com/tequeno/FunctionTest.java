package com.tequeno;

import com.tequeno.jdk8.function.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import static java.util.Comparator.comparing;

public class FunctionTest {

    @Test
    public void testApple() {
        List<Apple> appleList = new ArrayList<>();
        Apple apple = new Apple("red", 300D);
        Apple apple1 = new Apple("green", 150D);
        Apple apple2 = new Apple("blue", 100D);
        appleList.add(apple);
        appleList.add(apple1);
        appleList.add(apple2);

        AppleHandler appleHandler = new AppleHandler();
//        1
        appleHandler.filterApples(appleList, new RedAndHeavyApples());
//        2
        appleHandler.filterApples(appleList, new ApplePredicate() {
            @Override
            public boolean compare(Apple apple) {
                return apple.getWeight() > 100D;
            }
        });
//        3
        appleHandler.filterApples(appleList, appleInLambda -> appleInLambda.getWeight() > 100D);

        //输出
        appleList.forEach(appleInLambda -> {
            System.out.print(appleInLambda.getColor() + "\t");
            System.out.println(appleInLambda.getWeight());
        });
    }

    @Test
    public void testPredicate() {

        List<String> list = new ArrayList<>();
        list.add("green");
        list.add("blue");
        list.add("red");
        list.add("gray");
        list.add("orange");
        list.add("purple");

        PredicateHandler predicateHandler = new PredicateHandler();
        predicateHandler.filterList(list, strInLambda -> "ok".equals(strInLambda) || strInLambda.length() > 4);

        predicateHandler.filterPredicate("tequeno", strInLambda -> "ok".equals(strInLambda));

//        filterConsumer("ok", strInLambda -> System.out.println(strInLambda));
        predicateHandler.filterConsumer("ok", System.out::println);

//        filterFunction("ok", strInLambda -> strInLambda.length());
        predicateHandler.filterFunction("ok", String::length);

        predicateHandler.filterSupplier(() -> "ok", () -> "tequeno".length());

        predicateHandler.filterIntBinaryOperator(4, 5, (strInLeft, strInRight) -> strInLeft + strInRight);

        predicateHandler.filterBiFunction("ok", "tequeno", (strInLambda1, strInLambda2) -> strInLambda1 + " " + strInLambda2);

//        filterComparator("ik", "ok", (lambdat1, lambdat2) -> lambdat1.length() - lambdat2.length());
        predicateHandler.filterComparator("ok", "ok72fa", comparing(String::length));

        BiFunction<String, Double, Apple> f = Apple::new;
        List<Apple> appleList = new ArrayList();
        appleList.add(f.apply("1", 1d));
        appleList.add(f.apply("3", 3d));
        appleList.add(f.apply("2", 3d));
        appleList.add(f.apply("4", 4d));
        appleList.sort(comparing(Apple::getWeight).thenComparing(Apple::getColor));
        appleList.forEach((a) -> System.out.println(a.getColor() + "-" + a.getWeight()));
    }

}
