package com.tequeno.lottery;

import java.util.Random;

public class LotteryTicket {
    private final String[] redPools = {"01", "02", "03", "04", "05", "06", "07", "08", "09", //
            "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", //
            "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33"};

    private final String[] bluePools = {"01", "02", "03", "04", "05", "06", "07", //
            "08", "09", "10", "11", "12", "13", "14", "15", "16"};

    private final int RED_NUMS = 6;

    private final int RED_MAX = 33;

    private final int BLUE_MAX = 16;

    private final Random r = new Random();

    public String generateTicket() {
        StringBuilder builder = new StringBuilder();
        String tmp = "";
        for (int i = 0; i < RED_NUMS; i++) {
            do {
                tmp = redPools[r.nextInt(RED_MAX)];
            } while (builder.indexOf(tmp) > 0);
            builder.append(tmp + " ");
        }
        builder.deleteCharAt(builder.length() - 1).append("+" + bluePools[r.nextInt(BLUE_MAX)]);
        return builder.toString();
    }

    public String checkTicket() {
        return null;
    }
}
