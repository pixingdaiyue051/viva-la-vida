package com.tequeno.superchild;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ExtendTest {

    public static void main(String[] args) {
        AbstractPerson[] p = new AbstractPerson[2];
        p[0] = new Employee();
        p[0].write(10);
        p[1] = new Student();
        p[1].write("de");
//		total(p);
        List<AbstractPerson> list = new ArrayList<>();
        list.add(p[0]);
        totalC(list, 1);
    }

    public static void total(AbstractPerson[] p) {
        for (AbstractPerson person : p) {
            System.out.println(person.getHashCode(person.read()));
        }
    }

    public static void totalC(Collection<AbstractPerson> p) {
        for (AbstractPerson person : p) {
            System.out.println(person.getHashCode(person.read()));
        }
    }

    public static void totalC(Collection<? extends AbstractPerson> p, int d) {
        for (AbstractPerson person : p) {
            System.out.println(person.getHashCode(person.read()));
        }
    }
}
