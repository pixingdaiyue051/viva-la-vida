package com.tequeno.superchild;

public class Student extends AbstractPerson<String> {

    @Override
    public int getHashCode(String t) {
        return t.hashCode();
    }

}
