package com.tequeno.lottery;

public class Team {

    private String name;

    private Integer score;

    private Integer goal;

    private Integer lost;

    public Team(String name, Integer score, Integer goal, Integer lost) {
        this.name = name;
        this.score = score;
        this.goal = goal;
        this.lost = lost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getGoal() {
        return goal;
    }

    public void setGoal(Integer goal) {
        this.goal = goal;
    }

    public Integer getLost() {
        return lost;
    }

    public void setLost(Integer lost) {
        this.lost = lost;
    }

    public Integer abGoal() {
        return this.goal - this.lost;
    }
}
