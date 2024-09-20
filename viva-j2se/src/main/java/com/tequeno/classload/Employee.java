package com.tequeno.classload;

public class Employee implements Cloneable {

    private int param1;

    private String code;

    private Son son;

    public Employee() {
        System.out.println("Employee");
    }

    public Employee(int param1, String code, Son son) {
        this.param1 = param1;
        this.code = code;
        this.son = son;
    }

    public int getParam1() {
        return param1;
    }

    public String getCode() {
        return code;
    }

    public Son getSon() {
        return son;
    }

    public void run() {
        System.out.println("Employee run");
    }

    public class EmpInner {
        public EmpInner() {
            System.out.println("EmpInner");
        }

        public void run() {
            System.out.println("EmpInner run");
        }
    }

    public static class EmpStaticInner {
        public EmpStaticInner() {
            System.out.println("EmpStaticInner");
        }

        public void run() {
            System.out.println("EmpStaticInner run");
        }

    }

    @Override
    public String toString() {
        return "Employee{code='" + code + "'}";
    }

    @Override
    public Employee clone() throws CloneNotSupportedException {

        Object clone = super.clone();
        return (Employee) clone;
    }

//    @Override
//    public boolean equals(Object obj) {
//        return true;
//    }
//
//    @Override
//    public int hashCode() {
//        return super.hashCode();
//    }
}
