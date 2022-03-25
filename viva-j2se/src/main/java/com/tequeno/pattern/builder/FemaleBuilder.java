package com.tequeno.pattern.builder;

/**
 * 具体建造者
 */
public class FemaleBuilder implements PersonBuilder {

    private Person person;

    public FemaleBuilder() {
        person = new Person();
    }

    @Override
    public void buildHead() {
        person.setHead("female head");
    }

    @Override
    public void buildBody() {
        person.setBody("female body");
    }

    @Override
    public void buildFoot() {
        person.setFoot("female foot");
    }

    @Override
    public Person buildPerson() {
        return person;
    }
}
