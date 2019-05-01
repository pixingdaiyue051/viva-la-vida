package com.tequeno.test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Test {

    public static void main(String[] args) {
        String str = "robot,*obot,r**ot,r***t,r****,robot,*obot,r**ot,r***t,r****";

        long l1 = System.currentTimeMillis();
        String evaluateStr = EvaluateFacotrHandler.evaluate(str);
        System.out.println(evaluateStr);
        long l2 = System.currentTimeMillis();
        System.out.println(l2 - l1);

        long l3 = System.currentTimeMillis();
        String evaluateBuf = EvaluateFacotrHandler.evaluateBuf(str);
        System.out.println(evaluateBuf);
        long l4 = System.currentTimeMillis();
        System.out.println(l4 - l3);

        Integer a = 1;
        Integer b = 2;
        Integer c = 3;
        Integer d = 3;
        Integer e = 321;
        Integer f = 321;
        Long g = 3L;
        Long h = 2L;

        System.out.println(c == d);
        System.out.println(e == f);
        System.out.println(c == (a + b));
        System.out.println(c.equals(a + b));
        System.out.println(g == (a + b));
        System.out.println(g.equals(a + b));
        System.out.println(g.equals(a + h));
    }

    private static class EvaluateFacotrHandler {

        public static String evaluate(String s) {
            return queryStrHandler(s);
        }

        private static String queryStrHandler(String s) {
            String collectStr = Arrays.stream(s.split(","))
                    .distinct()
                    .sorted(Comparator.comparingInt((String str) -> str.length() - str.replaceAll("\\*", "").length()).reversed())
                    .collect(Collectors.joining(","));
            return collectStr;
        }

        public static String evaluateBuf(String s) {
            return queryStrHandlerBuf(s);
        }

        private static String queryStrHandlerBuf(String s) {
            List<String> list = Arrays.asList(s.split(","));
            list.sort((str1, str2) -> {
                int u1 = str1.length() - str1.replaceAll("\\*", "").length();
                int u2 = str2.length() - str2.replaceAll("\\*", "").length();
                return u2 - u1;
            });
            String collectStr = list.stream()
                    .distinct()
                    .collect(Collectors.joining(","));
            return collectStr;
        }
    }
}