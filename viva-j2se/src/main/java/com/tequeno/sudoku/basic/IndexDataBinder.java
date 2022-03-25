package com.tequeno.sudoku.basic;

public class IndexDataBinder {
    private int x;

    private int y;

    private int num;

    public IndexDataBinder(int x, int y, int num) {
        this.x = x;
        this.y = y;
        this.num = num;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void printInfoToConsole() {
        System.out.println("(" + x + "," + y + ")->" + num);
    }

    @Override
    public boolean equals(Object obj) {
        IndexDataBinder b = (IndexDataBinder) obj;
        return this.x == b.getX() && this.y == b.getY();
    }
}
