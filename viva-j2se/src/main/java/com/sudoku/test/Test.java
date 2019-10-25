package com.sudoku.test;

import com.sudoku.basic.Position;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

public class Test {
    public static void main(String[] args) {
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
        Long val = 34L;
        Calendar c = Calendar.getInstance();
        System.out.println(sdf.format(c.getTime()));
        c.add(Calendar.DAY_OF_MONTH, val.intValue() - 2 * val.intValue());
        System.out.println(sdf.format(c.getTime()));

        Position position = Position.P01;
        System.out.println(position.ordinal());
        System.out.println(position.name());
        Position position1 = Position.valueOf("P0101");
        System.out.println(position1.ordinal());
    }
}
