package com.pattern.strategy;

public class FlyWithWings implements FlyBehavior {
    @Override
    public void fly() {
        System.out.println("duck also can fly with their wings");
    }
}
