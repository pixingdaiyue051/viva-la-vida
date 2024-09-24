package com.tequeno.classload;

public class Employee {

    private String code;

    private String name;

    public Employee(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public Employee() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
