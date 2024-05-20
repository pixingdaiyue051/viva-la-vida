package com.tequeno.jdk8.function;

public class Apple {

    private String color;

    private double weight;

    public Apple() {
    }

    public Apple(String color) {
        this.color = color;
    }

    public Apple(double weight) {
        this.weight = weight;
    }

    public Apple(String color, double weight) {
        this.color = color;
        this.weight = weight;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
