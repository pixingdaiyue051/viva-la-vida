package com.tequeno.pattern.factory;

/**
 * 单一生产BMW的工厂
 * 为每一种车都创建一个工厂
 * 克服简单工厂生成集中的缺点
 * 每次有新的车要生产都可以不动原代码
 */
public interface Factory {
    AbstractBMW createBMW();
}