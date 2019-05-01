package com.jdk8.ifunctional;

public class RedAndHeavyApples implements ApplePredicate {
    @Override
    public boolean compare(Apple apple) {
        return ("red".equals(apple.getColor()) && apple.getWeight() > 150D);
    }
}
