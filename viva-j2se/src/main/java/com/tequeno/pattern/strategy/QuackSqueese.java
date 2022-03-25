package com.tequeno.pattern.strategy;

public class QuackSqueese implements QuackBehavior {
    @Override
    public void quack() {
        System.out.println("quack wa wa wa");
    }
}
