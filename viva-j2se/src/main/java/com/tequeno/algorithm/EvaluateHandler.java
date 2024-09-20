package com.tequeno.algorithm;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class EvaluateHandler {


    // reciprocal倒数
    public double sumReciprocal(int n) {
        if (n == 1) {
            return 1.0d;
        }
        return sumReciprocal(n - 1) + 1.0d / n;
    }

    // equalDifference等差
    // a1=1,d=1
    private int equalDifference(int n) {
        if (n == 1) {
            return 1;
        }
        return equalDifference(n - 1) + 1;
    }

    public int sumEqualDifference(int n) {
        int sum = 0;
        while (n > 0) {
            sum += equalDifference(n--);
        }
        return sum;
    }

    // equalDifference等比
    // a1=1,q=2
    private int equalRatio(int n) {
        if (n == 0) {
            return 1;
        }
        return equalRatio(n - 1) * 2;
    }

    public int sumEqualRatio(int n) {
        int sum = 0;
        while (n > -1) {
            sum += equalRatio(n--);
        }
        return sum;
    }

    // Fibonacci
    private int fibonacci(int n) {
        if (n < 3) {
            return 1;
        }
        return fibonacci(n - 1) + fibonacci(n - 2);
    }

    // 1+1+2+3+5+8+13+21+34+55+....=...
    public String sumFibonacci(int n) {
        int tmp, sum = 0;
        StringBuilder builder = new StringBuilder();
        while (n > 0) {
            tmp = fibonacci(n--);
            builder.insert(0, tmp + "+");
            sum += tmp;
        }
        if (builder.length() > 0) {
            builder.deleteCharAt(builder.length() - 1).append("=").append(sum);
            return builder.toString();
        }
        return "";
    }

    public void num() {
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
        String result = n.transfer(in, NumTransferEnum.DEC, NumTransferEnum.BIN);
        System.out.println(result);
        long l4 = System.currentTimeMillis();
        System.out.println(l4 - l3);
        result = n.transfer(in, NumTransferEnum.DEC, NumTransferEnum.OCT);
        System.out.println(result);
        result = n.transfer(in, NumTransferEnum.DEC, NumTransferEnum.HEX);
        System.out.println(result);
        result = n.transfer(in, NumTransferEnum.DEC, NumTransferEnum.DEC);
        System.out.println(result);

        String str = "54af";
        String transferRe = n.transfer(str, NumTransferEnum.HEX, NumTransferEnum.BIN);
        System.out.println(transferRe);

        long l1 = System.currentTimeMillis();
        int i = 20190511;
        String s = Integer.toBinaryString(i);
        System.out.println(s);
        long l2 = System.currentTimeMillis();
        System.out.println(l2 - l1);
        String s1 = Integer.toOctalString(i);
        System.out.println(s1);
        String s2 = Integer.toHexString(i);
        System.out.println(s2);
    }

    public String queryStrHandler(String s) {
        return Arrays.stream(s.split(","))
                .distinct()
                .sorted(Comparator.comparingInt((String str) -> str.length() - str.replaceAll("\\*", "").length()).reversed())
                .collect(Collectors.joining(","));
    }

    public String queryStrHandlerBuf(String s) {
        List<String> list = Arrays.asList(s.split(","));
        list.sort((str1, str2) -> {
            int u1 = str1.length() - str1.replaceAll("\\*", "").length();
            int u2 = str2.length() - str2.replaceAll("\\*", "").length();
            return u2 - u1;
        });
        return list.stream()
                .distinct()
                .collect(Collectors.joining(","));
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

    public String toPinyin(String chinese) {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);

        StringBuilder builder = new StringBuilder();
        char[] chars = chinese.toCharArray();
        for (char c : chars) {
            if (Character.isWhitespace(c)) {
                continue;
            }
            // c >= '一' && c <= '龥'
            if (c > 128) {
                try {
                    String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(c, format);
                    builder.append(pinyinArray[0]);
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {
                builder.append(c);
            }
        }
        return builder.toString();
    }

    public String to1stPinyin(String chinese) {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);

        StringBuilder builder = new StringBuilder();
        char[] chars = chinese.toCharArray();
        for (char c : chars) {
            if (Character.isWhitespace(c)) {
                continue;
            }
            // c >= '一' && c <= '龥'
            if (c > 128) {
                try {
                    String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(c, format);
                    builder.append(pinyinArray[0].charAt(0));
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {
                builder.append(c);
            }
        }
        return builder.toString();
    }

}
