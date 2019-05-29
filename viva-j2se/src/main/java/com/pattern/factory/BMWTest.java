package com.pattern.factory;

public class BMWTest {
    public static void main(String[] args) {
        simpleFactory();
        factoryMethod();
    }

    /**
     * 通过统一的工厂方法创建
     */
    public static void simpleFactory() {
        System.out.println("简单工厂模式");
        BMW320 bmw320 = new BMW320();
        BMW523 bmw523 = new BMW523();
        SimpleFactory simpleFactory = new SimpleFactory();
        AbstractBMW abstractBmw1 = simpleFactory.createBMW(320);
        AbstractBMW abstractBmw2 = simpleFactory.createBMW(523);
    }

    /**
     * 为每一种产品单独创建单一的工厂
     */
    public static void factoryMethod() {
        System.out.println("工厂方法模式");
        BMW320Factory bmw320Factory = new BMW320Factory();
        BMW320 bmw1 = bmw320Factory.createBMW();
        BMW523Factory bmw523Factory = new BMW523Factory();
        BMW523 bmw2 = bmw523Factory.createBMW();
    }

}