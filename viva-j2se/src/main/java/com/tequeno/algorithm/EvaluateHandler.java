package com.tequeno.algorithm;

import java.util.ArrayList;
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

    public String getMaxLengthSubString(String s) {
        String result = null;
        char[] ch = s.toCharArray();
        List<String> list = new ArrayList<>();
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < ch.length; i++) {
            for (int j = i; j < ch.length - 1; j++) {
                if (b.indexOf(String.valueOf(ch[j])) < 0) {
                    b.append(ch[j]);
                } else {
                    break;
                }
            }
            list.add(b.toString());
            b.delete(0, b.length());
        }
        int max = 0;
        if (!list.isEmpty()) {
            int len = 0;
            for (String str : list) {
                len = str.length();
                if (max < len) {
                    max = len;
                    result = str;
                }
            }
        }
        return result;
    }

    public String exchangeSec(int oriSec) {
        if (oriSec < 1) {
            // 不到1s
            return "0";
        }
        int minutes = (int) Math.floor(oriSec / 60D);
        if (minutes < 1) {
            // 不到1min
            return String.format("%02d", oriSec);
        }
        int hours = (int) Math.floor(minutes / 60D);
        if (hours < 1) {
            // 不到1h
            int second = oriSec - minutes * 60;
            return String.format("%02d:%02d", minutes, second);
        }
        int day = (int) Math.floor(hours / 24D);
        if (day < 1) {
            int second = oriSec - minutes * 60;
            minutes -= hours * 60;
            return String.format("%02d:%02d:%02d", hours, minutes, second);
        }
        int second = oriSec - minutes * 60;
        minutes -= hours * 60;
        hours -= day * 24;
        return String.format("%02d %02d:%02d:%02d", day, hours, minutes, second);
    }
}
