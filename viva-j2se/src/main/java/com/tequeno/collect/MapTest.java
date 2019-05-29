package com.tequeno.collect;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentHashMap;

public class MapTest {
    public static void main(String[] args) {
        hashMapOnDemo();
    }

    public static void hashMapOnDemo() {
        HashMap<String, String> map = new HashMap<>(11);
        String putBack = map.put("1", "53");
        putBack = map.put("2", null);
        putBack = map.put("3", "433");
        putBack = map.put("4", "433");
        putBack = map.put("5", "433");
        putBack = map.put("6", "433");
        putBack = map.put("7", "433");
        putBack = map.put("8", "433");
        map.get("4");
        System.out.println(putBack);
        map.entrySet();
    }

    public static void linkedHashMapOnDemo() {
        LinkedHashMap<String, String> map = new LinkedHashMap<>(11);
        String putBack = map.put("1", "53");
        putBack = map.put("2", null);
        putBack = map.put("3", "433");
        putBack = map.put("4", "433");
        putBack = map.put("5", "433");
        putBack = map.put("6", "433");
        putBack = map.put("7", "433");
        putBack = map.put("8", "433");
        map.get("4");
        map.clear();
        System.out.println(putBack);
        map.entrySet();
    }

    public static void hashTableOneDemo() {
        Hashtable<String, String> table = new Hashtable<>(11);
        table.put(null, null);
    }

    public static void concurrentHashTableOneDemo() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>(11);
        map.put(null, null);
    }

    private static void tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        cap = (n < 0) ? 1 : n + 1;
        System.out.println(cap);
    }

    private static int hash(Object key) {
        int i = key.hashCode();
        StringBuilder builder = new StringBuilder();
        decodeDec2Bin(builder, i);
        System.out.println(builder);
        i = i ^ i >>> 16;
        builder = new StringBuilder();
        decodeDec2Bin(builder, i);
        System.out.println(builder);
        return i;
    }

    private static void decodeDec2Bin(StringBuilder builder, int key) {
        int tmp = key >>> 1;
        if (tmp > 0) {
            decodeDec2Bin(builder, tmp);
            builder.append(key - tmp * 2);
        } else {
            builder.append(1);
        }
    }
}
