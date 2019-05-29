package com.pattern.strategy;

public class StrategyTest {
    public static void main(String[] args) {
        Duck duck = new MullardDuck();
        duck.display();
    }
}