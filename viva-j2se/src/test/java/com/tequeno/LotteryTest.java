package com.tequeno;

import com.tequeno.lottery.Lottery;
import com.tequeno.lottery.LotteryChecker;
import com.tequeno.lottery.LotteryGenerator;
import org.junit.Test;

import java.util.List;

public class LotteryTest {

    @Test
    public void testGenCheck() {
        LotteryGenerator generator = new LotteryGenerator();
        List<Lottery> list = generator.generate();
        Lottery lucky = generator.generateOne();
        System.out.println(lucky);

        LotteryChecker checker = new LotteryChecker();
        List<String> checkList = checker.check(list, lucky);
        for (int i = 0; i < list.size(); i++) {
            System.out.print(list.get(i));
            System.out.print('\t');
            System.out.println(checkList.get(i));
        }
    }

    @Test
    public void testDefault() {
        LotteryGenerator generator = new LotteryGenerator();
        List<Lottery> list = generator.generateDefault();
        String line = "07,18,11,19,01,05,07";
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
}
