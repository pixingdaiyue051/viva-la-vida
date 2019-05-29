package com.pattern.factory;

/**
 * 简单工厂类，把所有的车的生产都集中在这个工厂内部
 * 缺点是每次有新车出厂都要改原代码
 */
public class SimpleFactory {
    public AbstractBMW createBMW(int type) {
        switch (type) {
            case 320:
                return new BMW320();
            case 523:
                return new BMW523();
            default:
                return null;
        }
    }
}