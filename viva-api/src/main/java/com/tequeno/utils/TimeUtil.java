package com.tequeno.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeUtil {

    public final static String DATE_PATTERN = "yyyy-MM-dd";

    public final static String DATE_PATTERN_NUM = "yyyyMMdd";

    public final static String TIME_PATTERN = "HH:mm:ss";

    public final static String TIME_PATTERN_NUM = "HHmmss";

    public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public final static String DATE_TIME_PATTERN_NUM = "yyyyMMddHHmmss";

    public static String nowDate() {
        return LocalDate.now().toString();
    }

    public static String nowDateNum() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern(DATE_PATTERN_NUM));
    }

    public static String nowTime() {
        return LocalTime.now().toString();
    }

    public static String nowTimeNum() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN_NUM));
    }

    public static String now() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN));
    }

    /**
     * @param userTime   是否显示时分秒
     * @param weekOffset 星期位移,0表示当前所在的星期,负数表示往前推,正数往后推
     * @return 某星期的第一天和最后一天
     */
    public static String[] currentWeek(boolean userTime, int weekOffset) {
        String start;
        String end;
        if (userTime) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);
            LocalDateTime localDateTime = LocalDateTime.now();
            start = localDateTime.plusDays(1 - localDateTime.getDayOfWeek().getValue() + 7L * weekOffset).withHour(0).withMinute(0).withSecond(0).format(dateTimeFormatter);
            end = localDateTime.plusDays(7L * (weekOffset + 1) - localDateTime.getDayOfWeek().getValue()).withHour(23).withMinute(59).withSecond(59).format(dateTimeFormatter);
        } else {
            LocalDate localDate = LocalDate.now();
            start = localDate.plusDays(1 - localDate.getDayOfWeek().getValue() + 7L * weekOffset).toString();
            end = localDate.plusDays(7L * (weekOffset + 1) - localDate.getDayOfWeek().getValue()).toString();
        }
        return new String[]{start, end};
    }

    /**
     * @param userTime    是否显示时分秒
     * @param monthOffset 月位移,0表示当前月,负数表示往前推,正数往后推
     * @return 某月的第一天和最后一天
     */
    public static String[] currentMonth(boolean userTime, long monthOffset) {
        String start;
        String end;
        if (userTime) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);
            LocalDateTime localDateTime = LocalDateTime.now();
            LocalDateTime localDateTime3 = localDateTime.plusMonths(monthOffset).withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
            LocalDateTime localDateTime4 = localDateTime3.plusMonths(1).minusSeconds(1);
            start = localDateTime3.format(dateTimeFormatter);
            end = localDateTime4.format(dateTimeFormatter);
        } else {
            LocalDate localDate = LocalDate.now();
            LocalDate localDate3 = localDate.plusMonths(monthOffset).withDayOfMonth(1);
            LocalDate localDate4 = localDate3.plusMonths(1).minusDays(1);
            start = localDate3.toString();
            end = localDate4.toString();
        }
        return new String[]{start, end};
    }
}