package com.tequeno.str;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class IdHandler {

    private final static int MOD = 11;

    private final static String REG_ID = "[0-9]{17}[0-9|X|x]";

    private static final String[] NUM_POOL = new String[]{
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "0"
    };

    private final static String DEFAULT_START_DATE = "19111010";

    private static boolean preCheck(String idCard) {
        return idCard.matches(REG_ID);
    }

    private static String calc(String[] array) {
        int sum = 0;
        for (int i = 0; i < array.length - 1; i++) {
            Integer factor = IdEnum.getFactor(i);
            int idxNum = Integer.parseInt(array[i]);
            int tmp = factor * idxNum;
            sum += tmp;
        }
        int modeIdx = sum % MOD;
        return IdEnum.getOtp(modeIdx);
    }

    public static boolean check(String idCard) {
        boolean preChecked = preCheck(idCard);
        if (!preChecked) {
            System.out.println("错误身份证");
            return false;
        }
        String[] array = idCard.toUpperCase().split("");
        String modeStr = calc(array);
        String lastStr = array[array.length - 1];
        return lastStr.equals(modeStr);
    }

    public static List<String> randomList(int size) {
        Random r = new Random();
        List<String> result = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            result.add(singleOne(r));
        }
        return result;
    }

    public static String singleOne() {
        Random r = new Random();
        return singleOne(r);
    }

    private static String singleOne(Random random) {
        int first = random.nextInt(9) + 1;

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            builder.append(NUM_POOL[random.nextInt(NUM_POOL.length)]);
        }
        String late5Str = builder.toString();

        String randomDate = randomDate(DEFAULT_START_DATE, "");

        builder.delete(0, builder.length());
        for (int i = 0; i < 3; i++) {
            builder.append(NUM_POOL[random.nextInt(NUM_POOL.length)]);
        }
        String last3Str = builder.toString();

        String tmp = first + late5Str + randomDate + last3Str + "~";
        String[] array = tmp.split("");
        String modeStr = calc(array);
        return first + late5Str + randomDate + last3Str + modeStr;
    }

    public static String randomDate(String beginDate, String endDate) {
        final String DEFAULT = "20121211";
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
            Date start = format.parse(beginDate);
            Date end = null;
            if (null == endDate || "".equals(endDate)) {
                end = new Date();
            } else {
                end = format.parse(endDate);
            }
            // getTime()表示返回自 1970 年 1 月 1 日 00:00:00 GMT 以来此 Date 对象表示的毫秒数。
            if (start.getTime() >= end.getTime()) {
                return DEFAULT;
            }
            long date = randomTimestamp(start.getTime(), end.getTime());
            return format.format(new Date(date));
        } catch (ParseException e) {
            return DEFAULT;
        }
    }

    private static long randomTimestamp(long begin, long end) {
        long rtn = begin + (long) (Math.random() * (end - begin));
        // 如果返回的是开始时间和结束时间，则递归调用本函数查找随机值
        if (rtn == begin || rtn == end) {
            return randomTimestamp(begin, end);
        }
        return rtn;
    }

}
