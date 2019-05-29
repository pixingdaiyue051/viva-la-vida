package com.pattern.builder;

public class PersonTest {
    public static void main(String[] args) {
        PersonDirector pd = new PersonDirector();
        Person male = pd.buildPerson(new MaleBuilder());
        Person female = pd.buildPerson(new FemaleBuilder());
        System.out.println(male.toString());
        System.out.println(female.toString());
    }
}
