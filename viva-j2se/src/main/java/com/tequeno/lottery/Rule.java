package com.tequeno.lottery;

public enum Rule {

    WIN("胜", "负", 3, 0),
    GAIN("平", "平", 1, 1),
    LOSE("负", "胜", 0, 3);

    private String desc1;
    private String desc2;
    private Integer score1;
    private Integer score2;

    Rule(String desc1, String desc2, Integer score1, Integer score2) {
        this.desc1 = desc1;
        this.desc2 = desc2;
        this.score1 = score1;
        this.score2 = score2;
    }

    public String getDesc1() {
        return desc1;
    }

    public String getDesc2() {
        return desc2;
    }

    public Integer getScore1() {
        return score1;
    }

    public Integer getScore2() {
        return score2;
    }
}
