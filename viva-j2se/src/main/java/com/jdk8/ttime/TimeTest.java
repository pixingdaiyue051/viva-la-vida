package com.jdk8.ttime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeTest {
    public static void main(String[] args) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println("当前日期");
        LocalDate localDate = LocalDate.now();
        System.out.println(localDate);
        System.out.println("当前时间");
        LocalTime localTime = LocalTime.now();
        System.out.println(localTime);
        System.out.println("完整组合日期和时间");
        LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
        System.out.println(localDateTime.format(formatter));
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now.format(formatter));
        System.out.println("当前所在的星期(不带时分秒)");
        LocalDate localDate1 = localDate.plusDays(localDate.getDayOfWeek().getValue() - 6);
        LocalDate localDate2 = localDate.plusDays(7 - localDate.getDayOfWeek().getValue());
        System.out.println(localDate1 + "----" + localDate2);
        System.out.println("当前所在的月(不带时分秒)");
        LocalDate localDate3 = localDate.withDayOfMonth(1);
        LocalDate localDate4 = localDate3.plusMonths(1).minusDays(1);
        System.out.println(localDate3 + "----" + localDate4);
        System.out.println("当前所在的星期(包含时分秒)");
        LocalDateTime localDateTime1 = localDateTime.plusDays(localDateTime.getDayOfWeek().getValue() - 6).withHour(0).withMinute(0).withSecond(0);
        LocalDateTime localDateTime2 = localDateTime.plusDays(7 - localDateTime.getDayOfWeek().getValue()).withHour(23).withMinute(59).withSecond(59);
        System.out.println(localDateTime1.format(formatter) + "----" + localDateTime2.format(formatter));
        System.out.println("当前所在的月(包含时分秒)");
        LocalDateTime localDateTime3 = localDateTime.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
        LocalDateTime localDateTime4 = localDateTime3.plusMonths(1).minusSeconds(1);
        System.out.println(localDateTime3.format(formatter) + "----" + localDateTime4.format(formatter));
    }

}