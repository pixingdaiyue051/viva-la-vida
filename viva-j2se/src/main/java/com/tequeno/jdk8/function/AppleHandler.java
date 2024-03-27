package com.tequeno.jdk8.function;

import java.util.Iterator;
import java.util.List;

public class AppleHandler {

    public void filterApples(List<Apple> appleList, ApplePredicate predicate) {
        Iterator<Apple> iterator = appleList.iterator();
        while (iterator.hasNext()) {
            Apple apple = iterator.next();
            if (!predicate.compare(apple)) {
                iterator.remove();
            }
        }
    }
}