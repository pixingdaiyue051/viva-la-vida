package com.tequeno.lottery;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class MatchHandler {

    private Integer score1;
    private Integer score2;
    private Integer score3;
    private Integer score4;

    public void check(Team t1, Team t2, Team t3, Team t4) {

        // 记录原始分数
        score1 = t1.getScore();
        score2 = t2.getScore();
        score3 = t3.getScore();
        score4 = t4.getScore();

        List<Team> list = Arrays.asList(t1, t2, t3, t4);
        list.forEach(t -> System.out.print(t.getName() + ":" + t.getScore() + "(" + t.abGoal() + ") "));
        System.out.println();
        System.out.println("-----------------");

        final Rule[] rules = Rule.values();
        for (Rule rule1 : rules) {
            for (Rule rule2 : rules) {
                System.out.print(t1.getName() + ":" + rule1.getDesc1() + " ");
                System.out.print(t2.getName() + ":" + rule1.getDesc2() + " ");
                System.out.print(t3.getName() + ":" + rule2.getDesc1() + " ");
                System.out.print(t4.getName() + ":" + rule2.getDesc2() + "-->");
                t1.setScore(t1.getScore() + rule1.getScore1());
                t2.setScore(t2.getScore() + rule1.getScore2());
                t3.setScore(t3.getScore() + rule2.getScore1());
                t4.setScore(t4.getScore() + rule2.getScore2());
                cmpAndShow(list);
                reset(t1, t2, t3, t4);
            }
        }
        System.out.println("*****************");
    }

    private void cmpAndShow(List<Team> list) {
        list.sort(Comparator.comparingInt(Team::getScore).reversed());
        list.forEach(t -> System.out.print(t.getName() + ":" + t.getScore() + "(" + t.abGoal() + ") "));
        System.out.println();
    }

    private void reset(Team t1, Team t2, Team t3, Team t4) {
        t1.setScore(score1);
        t2.setScore(score2);
        t3.setScore(score3);
        t4.setScore(score4);
    }

    public void match1() {

        final Team team1 = new Team("日本", 3, 2, 2);
        final Team team2 = new Team("西班牙", 4, 8, 1);
        final Team team3 = new Team("哥斯达黎加", 3, 1, 7);
        final Team team4 = new Team("德国", 1, 2, 3);

        check(team1, team2, team3, team4);
    }

    public void match2() {

        final Team team1 = new Team("波兰", 4, 2, 0);
        final Team team2 = new Team("阿根廷", 3, 3, 2);
        final Team team3 = new Team("沙特阿拉伯", 3, 2, 3);
        final Team team4 = new Team("墨西哥", 1, 0, 2);

        check(team1, team2, team3, team4);
    }

    public void match3() {

        final Team team1 = new Team("葡萄牙", 6, 5, 2);
        final Team team2 = new Team("韩国", 1, 2, 3);
        final Team team3 = new Team("加纳", 3, 5, 5);
        final Team team4 = new Team("乌拉圭", 1, 0, 2);

        check(team1, team2, team3, team4);
    }
}
