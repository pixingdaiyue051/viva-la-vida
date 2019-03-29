package com.jdk8.ifunctional;

@FunctionalInterface
public interface CommonPredicate<T> {
    boolean process(T t);
}
