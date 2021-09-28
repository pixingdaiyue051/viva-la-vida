package com.tequeno.num;

public class NumTest {

    public static void main(String[] args) {
        NumTest numTest = new NumTest();
        int sec = 0;

        System.out.println(numTest.exchangeSec(sec));

        sec = 59;
        System.out.println(numTest.exchangeSec(sec));

        sec = 61;
        System.out.println(numTest.exchangeSec(sec));

        sec = 3599;
        System.out.println(numTest.exchangeSec(sec));

        sec = 3601;
        System.out.println(numTest.exchangeSec(sec));

        sec = 86399;
        System.out.println(numTest.exchangeSec(sec));

        sec = 86401;
        System.out.println(numTest.exchangeSec(sec));
    }

    private String exchangeSec(int oriSec) {
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
