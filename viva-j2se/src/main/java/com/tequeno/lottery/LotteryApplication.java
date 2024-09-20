package com.tequeno.lottery;

import java.util.List;

public class LotteryApplication {

    private static void run(String line) {
        LotteryGenerator generator = new LotteryGenerator();
        List<Lottery> list = generator.generateDefault();
        Lottery lucky = generator.generateOne(line);
        System.out.println(lucky);
        LotteryChecker checker = new LotteryChecker();
        List<String> checkList = checker.check(list, lucky);
        for (int i = 0; i < list.size(); i++) {
            System.out.print(list.get(i));
            System.out.print('\t');
            System.out.println(checkList.get(i));
        }
    }

    public static void main(String[] args) {
        StringBuilder builder = new StringBuilder();
        for (String arg : args) {
            builder.append(arg);
            builder.append(",");
        }
        builder.deleteCharAt(builder.length() - 1);
        run(builder.toString());
    }
}
