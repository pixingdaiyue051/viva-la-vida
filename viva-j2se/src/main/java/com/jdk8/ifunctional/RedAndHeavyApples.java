package com.jdk8.ifunctional;

public class RedAndHeavyApples implements ApplePredicate {
    @Override
    public boolean compare(Apple apple) {
        return (apple.getColor().equals("red") && apple.getWeight() > 150D);
    }
}
