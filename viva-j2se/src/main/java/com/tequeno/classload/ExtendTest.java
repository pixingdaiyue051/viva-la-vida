package com.tequeno.classload;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ExtendTest {

    public static void main(String[] args) {
        try {
            Employee employee = new Employee("e1","e2");
            employee.seteArg1("e111");
            employee.setArg1("111");

            // 反射获取方法
            Class<?> superclass = employee.getClass().getSuperclass();
            Object o = superclass.newInstance();
            Method method = superclass.getDeclaredMethod("getArg1");
            Object invoke = method.invoke(employee);
            System.out.println(invoke);
            // 反射获得字段
            Field field = superclass.getDeclaredField("arg1");
            field.setAccessible(true);
            Object o2 = field.get(employee);
            System.out.println(o2);

            Class<? extends Employee> aClass = employee.getClass();
            Employee o1 = aClass.newInstance();
            Method method1 = aClass.getDeclaredMethod("geteArg1");
            Object invoke1 = method1.invoke(employee);
            System.out.println(invoke1);

            Field field1 = aClass.getDeclaredField("eArg1");
            field1.setAccessible(true);
            Object o3 = field1.get(employee);
            System.out.println(o3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
