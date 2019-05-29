package com.tequeno.test;

import com.tequeno.num.NumTransferHandler;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Test {

    public static void main(String[] args) {
        Integer a = 1;
        Integer b = 2;
        Integer c = 3;
        Integer d = 3;
        Integer e = 321;
        Integer f = 321;
        Long g = 3L;
        Long h = 2L;

        System.out.println(c == d);//true
        System.out.println(e == f);//false
        System.out.println(c == (a + b));//true
        System.out.println(c.equals(a + b));//true
        System.out.println(g == (a + b));//true
        System.out.println(g.equals(a + b));//false
        System.out.println(g.equals(a + h));//true

        long l3 = System.currentTimeMillis();
        NumTransferHandler n = new NumTransferHandler();
        String in = "20190511";
        String result = n.transfer(in, NumTransferHandler.DecDispEnum.DEC, NumTransferHandler.DecDispEnum.BIN);
        System.out.println(result);
        long l4 = System.currentTimeMillis();
        System.out.println(l4-l3);
        result = n.transfer(in, NumTransferHandler.DecDispEnum.DEC, NumTransferHandler.DecDispEnum.OCT);
        System.out.println(result);
        result = n.transfer(in, NumTransferHandler.DecDispEnum.DEC, NumTransferHandler.DecDispEnum.HEX);
        System.out.println(result);
        result = n.transfer(in, NumTransferHandler.DecDispEnum.DEC, NumTransferHandler.DecDispEnum.DEC);
        System.out.println(result);

        String str = "54af";
        String transferRe = n.transfer(str, NumTransferHandler.DecDispEnum.HEX, NumTransferHandler.DecDispEnum.BIN);
        System.out.println(transferRe);

        long l1 = System.currentTimeMillis();
        int i = 20190511;
        String s = Integer.toBinaryString(i);
        System.out.println(s);
        long l2 = System.currentTimeMillis();
        System.out.println(l2-l1);
        String s1 = Integer.toOctalString(i);
        System.out.println(s1);
        String s2 = Integer.toHexString(i);
        System.out.println(s2);


        StringBuilder builder = new StringBuilder();
        builder.append("1");
    }

//        String str = "robot,*obot,r**ot,r***t,r****,robot,*obot,r**ot,r***t,r****";
//
//        long l1 = System.currentTimeMillis();
//        String evaluateStr = EvaluateFacotrHandler.evaluate(str);
//        System.out.println(evaluateStr);
//        long l2 = System.currentTimeMillis();
//        System.out.println(l2 - l1);
//
//        long l3 = System.currentTimeMillis();
//        String evaluateBuf = EvaluateFacotrHandler.evaluateBuf(str);
//        System.out.println(evaluateBuf);
//        long l4 = System.currentTimeMillis();
//        System.out.println(l4 - l3);
//
//    private static class EvaluateFacotrHandler {
//
//        public static String evaluate(String s) {
//            return queryStrHandler(s);
//        }
//
//        private static String queryStrHandler(String s) {
//            String collectStr = Arrays.stream(s.split(","))
//                    .distinct()
//                    .sorted(Comparator.comparingInt((String str) -> str.length() - str.replaceAll("\\*", "").length()).reversed())
//                    .collect(Collectors.joining(","));
//            return collectStr;
//        }
//
//        public static String evaluateBuf(String s) {
//            return queryStrHandlerBuf(s);
//        }
//
//        private static String queryStrHandlerBuf(String s) {
//            List<String> list = Arrays.asList(s.split(","));
//            list.sort((str1, str2) -> {
//                int u1 = str1.length() - str1.replaceAll("\\*", "").length();
//                int u2 = str2.length() - str2.replaceAll("\\*", "").length();
//                return u2 - u1;
//            });
//            String collectStr = list.stream()
//                    .distinct()
//                    .collect(Collectors.joining(","));
//            return collectStr;
//        }
//    }
}