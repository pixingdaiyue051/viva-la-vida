package com.tequeno.superchild;

public class Employee extends Person {
    private String eArg1;
    private String eArg2;

    public String geteArg1() {
        return eArg1;
    }

    public void seteArg1(String eArg1) {
        this.eArg1 = eArg1;
    }

    public String geteArg2() {
        return eArg2;
    }

    public void seteArg2(String eArg2) {
        this.eArg2 = eArg2;
    }

    public Employee() {
    }

    public Employee(String eArg1, String eArg2) {
        super();
        this.eArg1 = eArg1;
        this.eArg2 = eArg2;
    }

    public Employee(String arg1, String arg2, String eArg1, String eArg2) {
        super(arg1, arg2);
        this.eArg1 = eArg1;
        this.eArg2 = eArg2;
    }
}
