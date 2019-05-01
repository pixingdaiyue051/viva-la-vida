package com.jdk8.ifunctional;

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
//        1
        filterApples(appleList, new RedAndHeavyApples());
//        2
        filterApples(appleList, new ApplePredicate() {
            @Override
            public boolean compare(Apple apple) {
                return apple.getWeight() > 100D;
            }
        });
//        3
        filterApples(appleList, appleInLambda -> appleInLambda.getWeight() > 100D);

        //输出
        appleList.forEach(appleInLambda -> {
            System.out.print(appleInLambda.getColor() + "\t");
            System.out.println(appleInLambda.getWeight());
        });
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

}
