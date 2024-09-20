package com.tequeno.lottery;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum LotteryRule {

    ONE("6+1", "一等奖(6+1) 浮动"),
    TWO("6+0", "二等奖(6+0) 浮动"),
    THREE("5+1", "三等奖(5+1) 3000元"),
    FOUR_I("5+0", "四等奖(5+0) 200元"),
    FOUR_II("4+1", "四等奖(4+1) 200元"),
    FIVE_I("4+0", "五等奖(4+0) 10元"),
    FIVE_II("3+1", "五等奖(3+1) 10元"),
    SIX_I("2+1", "六等奖(2+1) 5元"),
    SIX_II("1+1", "六等奖(1+1) 5元"),
    SIX_III("0+1", "六等奖(0+1) 5元"),
    ;
    private final String code;
    private final String desc;

    LotteryRule(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String getDescByCode(String code) {
        return RuleHolder.map.getOrDefault(code, "未中奖 0元");
    }

    private static class RuleHolder {
        private final static Map<String, String> map;

        static {
            map = Arrays.stream(LotteryRule.values()).collect(Collectors.toMap(r -> r.code, r -> r.desc));
        }
    }
}
