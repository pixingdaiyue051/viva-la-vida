package com.tequeno.classload;

public class Student extends Person {
    private String sArg1;
    private String sArg2;

    public String getsArg1() {
        return sArg1;
    }

    public void setsArg1(String sArg1) {
        this.sArg1 = sArg1;
    }

    public String getsArg2() {
        return sArg2;
    }

    public void setsArg2(String sArg2) {
        this.sArg2 = sArg2;
    }


    public Student(String arg1, String arg2, String sArg1, String sArg2) {
        super(arg1, arg2);
        this.sArg1 = sArg1;
        this.sArg2 = sArg2;
    }
}
