package com.tequeno.pattern.strategy;

public class MullardDuck extends Duck{

    public MullardDuck() {
        setFlyBehavior(new FlyWithWings());
        setQuackBehavior(new QuackSilence());
    }

    @Override
    public void display() {
        performFly();
        performQuack();
        swim();
    }
}
