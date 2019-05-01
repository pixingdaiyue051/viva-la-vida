package com.tequeno.collect;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MapTest {
    public static void main(String[] args) {
//		hashMapOnDemo();
//		hashTableOneDemo();
        concurrentHashTableOneDemo();
    }

    public static void hashMapOnDemo() {
        Map<String, String> map = new HashMap<>(16);
        map.put(null, null);
    }

    public static void hashTableOneDemo() {
        Map<String, String> table = new Hashtable<>(16);
        table.put(null, null);
    }

    public static void concurrentHashTableOneDemo() {
        Map<String, String> map = new ConcurrentHashMap<>(16);
        map.put(null, null);
    }
}
