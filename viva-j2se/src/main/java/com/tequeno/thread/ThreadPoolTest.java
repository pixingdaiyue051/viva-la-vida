package com.tequeno.thread;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.IntStream;

public class ThreadPoolTest {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService pool = Executors.newCachedThreadPool();

        Map<Integer, String> map = new HashMap<>(100);
        final String VALUE = "VALUE";
        List<Object> list = new ArrayList<>();
        IntStream.rangeClosed(1, 9)
                .forEach(i -> pool.execute(() -> IntStream.rangeClosed(1, 9).forEach(ii -> {
                    map.put(i * ii, VALUE);
                    list.add(i*ii);
                })));

//        map.forEach((k, v) -> {
//            if (k == 2) {
//                map.remove(k);
//            }
//        });

        pool.shutdown();
        boolean termination = pool.awaitTermination(3, TimeUnit.SECONDS);
        if (termination) {
            System.out.println(map.size());
            map.forEach((k, v) -> System.out.printf("%d-%s\n", k, v));
            System.out.println(list.size());
            list.forEach(System.out::println);
        }

    }
}
