package com.tequeno.str;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StringTest {
    public static void main(String[] args) {
//        getMaxLengthSubString("qwertyuiopasdfghjklzxcvbnm");

//        String idCard = "632323190605261343";
//        idCard = "510921197912177590";
//        idCard = "510704198205292714";
//        boolean check = IdHandler.check(idCard);
//        System.out.println(check);

        String idCard = IdHandler.singleOne();
        System.out.println(idCard);
        boolean check = IdHandler.check(idCard);
        System.out.println(check);

        List<String> randomList = IdHandler.randomList(10);
        randomList.forEach(System.out::println);
    }

    public static String getMaxLengthSubString(String s) {
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

}
