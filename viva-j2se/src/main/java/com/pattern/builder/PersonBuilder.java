package com.pattern.builder;

/**
 * 抽象建造者，仅仅定义需要创建什么产品
 */
public interface PersonBuilder {
    void buildHead();

    void buildBody();

    void buildFoot();

    Person buildPerson();
}
