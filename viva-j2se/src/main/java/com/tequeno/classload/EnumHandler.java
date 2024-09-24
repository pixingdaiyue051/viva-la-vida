package com.tequeno.classload;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum EnumHandler {

    ONE("01", "壹"),
    TWO("02", "贰"),
    THREE("03", "叁"),
    FOUR("04", "肆"),
    ;

    {
        System.out.println("EnumHandler 普通代码块");
    }

    static {
        System.out.println("EnumHandler 静态代码块");
    }

    EnumHandler(String code, String name) {
        System.out.println("EnumHandler 构造方法");
        this.code = code;
        this.name = name;
    }

    private final String code;

    private final String name;

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static String getNameByCode(String code) {
        return EnumInnerHolder.map.get(code);
    }

    private static class EnumInnerHolder {

        private final static Map<String, String> map = new HashMap<>();

        private EnumInnerHolder() {
            System.out.println("EnumInnerHolder 构造方法");
        }

        {
            System.out.println("EnumInnerHolder 普通代码块");
        }

        static {
            System.out.println("EnumInnerHolder 静态代码块");
            EnumHandler[] enums = EnumHandler.values();
            Arrays.stream(enums).forEach(item -> map.put(item.code, item.name));
        }
    }
}