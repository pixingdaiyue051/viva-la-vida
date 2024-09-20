package com.tequeno.lottery;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class LotteryChecker {

    public List<String> check(List<Lottery> list, Lottery lucky) {
        List<String> result = new ArrayList<>(list.size());
        for (Lottery l : list) {
            result.add(this.check(l, lucky));
        }
        return result;
    }

    public String check(Lottery lottery, Lottery lucky) {
        Set<String> redSet = lottery.getRedSet();
        Set<String> luckyRedSet = lucky.getRedSet();
        int red = 0;
        for (String s : redSet) {
            if (luckyRedSet.contains(s)) {
                red++;
            }
        }
        int blue = lucky.getBlue().equals(lottery.getBlue()) ? 1 : 0;
        String code = String.format("%d+%d", red, blue);
        return LotteryRule.getDescByCode(code);
    }
}
