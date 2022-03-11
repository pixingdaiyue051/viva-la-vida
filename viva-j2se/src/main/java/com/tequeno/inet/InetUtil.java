package com.tequeno.inet;

import java.util.Random;
import java.util.UUID;

public class InetUtil {

    private static final String[] NUM_POOL = new String[]{
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "0"
    };

    public static String random(int length) {
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            builder.append(NUM_POOL[random.nextInt(NUM_POOL.length)]);
        }
        return builder.toString();
    }

    public static String uuid() {
        return UUID.randomUUID().toString();
    }
}
