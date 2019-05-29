package com.pattern.strategy;

public class QuackSilence implements QuackBehavior {
    @Override
    public void quack() {
        System.out.println("mute duck");
    }
}
