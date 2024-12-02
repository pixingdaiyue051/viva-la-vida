package com.tequeno.classload;

import java.io.Serializable;

public class Pack implements Serializable {

    private static final long serialVersionUID = 8648122787728697336L;

    private long id;

    private transient String param1;

    private Integer param2;

    private Employee employee;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getParam1() {
        return param1;
    }

    public void setParam1(String param1) {
        this.param1 = param1;
    }

    public Integer getParam2() {
        return param2;
    }

    public void setParam2(Integer param2) {
        this.param2 = param2;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public String toString() {
        return "Pack{" +
                "id=" + id +
                ", param1='" + param1 + '\'' +
                ", param2=" + param2 +
                ", employee=" + employee +
                '}';
    }
}
