package com.tequeno.jdk8.time;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class TimeHandler {

    private final static String PATTERN = "yyyy-MM-dd HH:mm:ss";

    private final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(PATTERN);

    /**
     * LocalDate    当前日期
     * LocalTime    当前时间
     * LocalDateTime    当前时间和日期
     */
    public void normal() {
        System.out.println("当前日期");
        LocalDate localDate = LocalDate.now();
        System.out.println(localDate);
        System.out.println("当前时间");
        LocalTime localTime = LocalTime.now();
        System.out.println(localTime);
        System.out.println("完整组合日期和时间");
        LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
        System.out.println(localDateTime.format(FORMATTER));
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now.format(FORMATTER));
        System.out.println("当前所在的星期(不带时分秒)");
        LocalDate localDate1 = localDate.minusDays(localDate.getDayOfWeek().getValue() - 1);
        LocalDate localDate2 = localDate1.plusDays(6);
        System.out.println(localDate1 + "----" + localDate2);
        System.out.println("当前所在的星期(包含时分秒)");
        LocalDateTime localDateTime1 = localDateTime.minusDays(localDate.getDayOfWeek().getValue() - 1).withHour(0).withMinute(0).withSecond(0);
        LocalDateTime localDateTime2 = localDateTime1.plusDays(7).minusSeconds(1);
        System.out.println(localDateTime1.format(FORMATTER) + "----" + localDateTime2.format(FORMATTER));
        System.out.println("当前所在的月(不带时分秒)");
        LocalDate localDate3 = localDate.withDayOfMonth(1);
        LocalDate localDate4 = localDate3.plusMonths(1).minusDays(1);
        System.out.println(localDate3 + "----" + localDate4);
        System.out.println("当前所在的月(包含时分秒)");
        LocalDateTime localDateTime3 = localDateTime.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
        LocalDateTime localDateTime4 = localDateTime3.plusMonths(1).minusSeconds(1);
        System.out.println(localDateTime3.format(FORMATTER) + "----" + localDateTime4.format(FORMATTER));
    }

    /**
     * 计算时间差
     * Period 计算 LocalDate
     * Duration 计算 LocalDate LocalTime LocalDateTime
     */
    public void diff() {
        LocalDate date1 = LocalDate.now();
        LocalDate date2 = date1.withDayOfMonth(2).plusWeeks(1);
        System.out.println(date1.isBefore(date2));

        LocalTime time1 = LocalTime.now();
        LocalTime time2 = time1.withHour(4).withMinute(20).withSecond(10);
        System.out.println(time1.equals(time2));

        LocalDateTime d1 = LocalDateTime.now();
        LocalDateTime d2 = d1.withHour(1).plusDays(1).minusSeconds(20).plusHours(10);
        System.out.println(d2.isAfter(d1));

        Duration duration = Duration.between(d1, d2);
        System.out.println(duration.toDays());
        System.out.println(duration.toHours());
        System.out.println(duration.toMinutes());
        System.out.println(duration.toSeconds());
        System.out.println(duration.toMillis());

        Period period = Period.between(date1, date2);
        System.out.println(period.getYears());
        System.out.println(period.getMonths());
        System.out.println(period.getDays());

    }

    public void timeMillis() {
        Instant now = Instant.now();
        System.out.println(now.getNano());
        System.out.println(now.getEpochSecond());
        System.out.println(now.toEpochMilli());

        Instant plussed = now.plus(Duration.ofHours(1));
        System.out.println(plussed);

        Instant plus = now.plus(Period.ofDays(1));
        System.out.println(plus);

    }
}
