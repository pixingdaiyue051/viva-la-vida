package com.tequeno.jdk8.function;

public class RedAndHeavyApples implements ApplePredicate {
    @Override
    public boolean compare(Apple apple) {
        return ("red".equals(apple.getColor()) && apple.getWeight() > 150D);
    }
}
