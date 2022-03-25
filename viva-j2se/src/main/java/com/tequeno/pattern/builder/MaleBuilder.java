package com.tequeno.pattern.builder;

/**
 * 具体建造者
 */
public class MaleBuilder implements PersonBuilder {

    private Person person;

    public MaleBuilder() {
        person = new Person();
    }

    @Override
    public void buildHead() {
        person.setHead("male head");
    }

    @Override
    public void buildBody() {
        person.setBody("male body");
    }

    @Override
    public void buildFoot() {
        person.setFoot("male foot");
    }

    @Override
    public Person buildPerson() {
        return person;
    }
}
