package com.tequeno.superchild;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExtendTest {

	public static void main(String[] args) {
		Person[] p = new Person[2];
		p[0] = new Employee();
		p[0].write(10);
		p[1] = new Student();
		p[1].write("de");
//		total(p);
		List<Person> list = new ArrayList<>();
		list.add(p[0]);
		totalC(list,1);
	}

	public static void total(Person[] p) {
		for (Person person : p) {
			System.out.println(person.getHashCode(person.read()));
		}
	}

	public static void totalC(Collection<Person> p) {
		for (Person person : p) {
			System.out.println(person.getHashCode(person.read()));
		}
	}

	public static void totalC(Collection<? extends Person> p, int d) {
		for (Person person : p) {
			System.out.println(person.getHashCode(person.read()));
		}
	}
}
