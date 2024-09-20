package com.tequeno.lottery;

import java.util.Set;

/**
 * 每周二 周四 周日开奖
 * 双色球投注区分为红色球号码区和蓝色球号码区
 * 红色球号码区由1-33共三十三个号码组成
 * 蓝色球号码区由1-16共十六个号码组成
 * 投注时选择6个红色球号码和1个蓝色球号码组成一注进行单式投注 每注金额人民币2元
 */
public class Lottery {

    private Set<String> redSet;

    private String blue;

    public Lottery() {
    }

    public Lottery(Set<String> redSet, String blue) {
        this.redSet = redSet;
        this.blue = blue;
    }

    public Set<String> getRedSet() {
        return redSet;
    }

    public String getBlue() {
        return blue;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (String s : redSet) {
            builder.append(s);
            builder.append(" ");
        }
        builder.append(blue);
        return builder.toString();
    }
}
