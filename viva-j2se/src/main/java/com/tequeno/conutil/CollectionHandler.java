package com.tequeno.conutil;

import java.util.*;

public class CollectionHandler {

    public void listOneDemo() {
        ArrayList<String> list = new ArrayList<>();
        list.add(null);
        list.add(null);
        list.add("1");
        list.add("1");
        list.add("2");
        list.add("3");
        list.forEach(System.out::println);
    }

    public void linkedlistOneDemo() {
        LinkedList<String> list = new LinkedList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        list.add("6");
        list.push("0");
        // 获取首节点
//        peek,peekFirst,element,getFirst
        list.peek();
        list.peekFirst();
        list.element();
        list.getFirst();
        // 获取首节点
//        poll,pollFirst,remove,removeFirst,pop
        list.poll();
        list.pollFirst();
        list.remove();
        list.removeFirst();
        list.pop();

        list.peekLast();
        list.pollLast();

        Stack<String> s = new Stack<>();
        Queue<String> q = new ArrayDeque<>();
        list.forEach(System.out::println);
    }

    public void setOneDemo() {
        HashSet<String> set = new HashSet<>(11);
        set.add(null);
        set.add(null);
        set.add("1");
        set.add("1");
        set.add("2");
        set.add("2");
        set.add("3");
        set.add("3");
        set.forEach(System.out::println);

        TreeSet<String> treeSet = new TreeSet<>();
        TreeMap treeMap = new TreeMap();
    }

}
