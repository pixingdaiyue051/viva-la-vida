package com.tequeno.classload;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum TestEnum {

    ONE("01", 1001L),

    TWO("02", 1002L),

    THREE("03", 1003L),

    FOUR("04", 1002L),
    ;

    {
        System.out.println("TestEnum blank area");
    }

    static {
        System.out.println("TestEnum static area");
    }

    TestEnum(String prefix, Long prisonId) {
        System.out.println("TestEnum constructor");
        this.prefix = prefix;
        this.prisonId = prisonId;
    }

    private String prefix;

    private Long prisonId;

    public String getPrefix() {
        return prefix;
    }

    public Long getPrisonId() {
        return prisonId;
    }

    public static Long fetchPrisonId(String prefix) {
        return InnerHolder.map.get(prefix);
    }

    private static class InnerHolder {
        private final static Map<String, Long> map = new HashMap<>(5);

        private InnerHolder() {
            System.out.println("constructor");
        }

        {
            System.out.println("blank area");
        }

        static {
            System.out.println("static area");
            TestEnum[] enums = TestEnum.values();
            Arrays.stream(enums).forEach(item -> map.put(item.prefix, item.prisonId));
        }
    }
}