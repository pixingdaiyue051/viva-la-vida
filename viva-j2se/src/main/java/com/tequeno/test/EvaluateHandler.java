package com.tequeno.test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class EvaluateHandler {

    public String evaluate(String s) {
        return queryStrHandler(s);
    }

    private String queryStrHandler(String s) {
        String collectStr = Arrays.stream(s.split(","))
                .distinct()
                .sorted(Comparator.comparingInt((String str) -> str.length() - str.replaceAll("\\*", "").length()).reversed())
                .collect(Collectors.joining(","));
        return collectStr;
    }

    public String evaluateBuf(String s) {
        return queryStrHandlerBuf(s);
    }

    private String queryStrHandlerBuf(String s) {
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
