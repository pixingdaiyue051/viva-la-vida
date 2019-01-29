package com.jdk8.predicate.apple;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AppleTest {
    public static void main(String[] args) {
        List<Apple> appleList = new ArrayList<>();
        Apple apple = new Apple("red", 300D);
        Apple apple1 = new Apple("green", 150D);
        Apple apple2 = new Apple("blue", 100D);
        appleList.add(apple);
        appleList.add(apple1);
        appleList.add(apple2);
        //1
//        filterApples(appleList, new RedAndHeavyApples());
        //2
//        filterApples(appleList, new ApplePredicate() {
//            @Override
//            public boolean compare(Apple apple) {
//                return apple.getWeight() > 100D
//            }
//        });
        //3
//        filterApples(appleList, $apple -> $apple.getWeight() > 100D);
        //4
        filterList(appleList, _apple -> _apple.getWeight() > 100D && _apple.getColor().equals("red"));

        //输出
        appleList.forEach($apple -> {
            System.out.print($apple.getColor() + "\t");
            System.out.println($apple.getWeight());
        });

        List<String> list = new ArrayList<>();
        list.add("111");
        list.add("222");
        list.add("333");
        filterList(list, _str -> "333".equals(_str));
        list.forEach(System.out::println);
    }

    public static void filterApples(List<Apple> appleList, ApplePredicate predicate) {
        Iterator<Apple> iterator = appleList.iterator();
        while (iterator.hasNext()) {
            Apple apple = iterator.next();
            if (!predicate.compare(apple)) {
                iterator.remove();
            }
        }
    }

    public static <T> void filterList(List<T> tList, CommonPredicate<T> p) {
        Iterator<T> iterator = tList.iterator();
        while (iterator.hasNext()) {
            T t = iterator.next();
            if (!p.compare(t)) {
                iterator.remove();
            }
        }
    }

}
