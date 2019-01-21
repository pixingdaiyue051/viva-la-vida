package com.tequeno.collect;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class CollectionTest {
	public static void main(String[] args) {
		listOneDemo();
//		setOneDemo();
	}

	public static void listOneDemo() {
		List<String> list = new ArrayList<>();
		list.add(null);
//		list.add(null);
//		list.add("1");
//		list.add("1");
//		list.add("2");
//		list.add("3");
		System.out.println(list.size());
	}
	
	public static void linkedlistOneDemo() {
		List<String> list = new LinkedList<>();
		list.add(null);
		list.add(null);
		list.add("1");
		list.add("1");
		list.add("2");
		list.add("3");
		System.out.println(list.size());
		for (String string : list) {
			System.out.println(string);
		}
	}
	
	public static void setOneDemo() {
		Set<String> set = new HashSet<>();
		set.add(null);
		set.add(null);
		set.add("1");
		set.add("1");
		set.add("2");
		set.add("2");
		set.add("3");
		set.add("3");
		System.out.println(set.size());
	}
	
}
