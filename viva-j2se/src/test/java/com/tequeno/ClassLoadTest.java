package com.tequeno;

import com.tequeno.classload.Employee;
import com.tequeno.classload.EnumHandler;
import com.tequeno.classload.Son;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;

public class ClassLoadTest {

    private static final Logger log = LoggerFactory.getLogger(ClassLoadTest.class);

    /**
     * Grandpa - 静态代码块
     * Father - 静态代码块
     * Son - 静态代码块
     * Son - 静态方法
     */
    @Test
    public void testStaticRun() {
        Son.staticRun();
        Son.staticRun();
    }

    /**
     * Grandpa - 静态代码块
     * Father - 静态代码块
     * Son - 静态代码块
     * Grandpa - 普通代码块
     * Grandpa - 构造方法
     * Father - 普通代码块
     * Father - 构造方法
     * Son - 普通代码块
     * Son - 构造方法
     * Son - 方法
     */
    @Test
    public void testMethodRun() {
        Son son = new Son();
        son.run();
        son.run();
    }

    @Test
    public void testInnerClass() {
        Employee emp = new Employee();
        emp.run();

        Employee.EmpInner empInner = emp.new EmpInner();
        empInner.run();

        Employee.EmpStaticInner empStaticInner = new Employee.EmpStaticInner();
        empStaticInner.run();
    }

    @Test
    public void testObj() {
        try {
//            Employee emp = new Employee(132, "100", new Son());
//            Employee emp1 = emp.clone();
//
//            log.info("clone {} {} {}", emp.getParam1() == emp1.getParam1(), emp.getCode() == emp1.getCode(), emp.getSon() == emp1.getSon());
//            log.info("hashCode {} {}", emp.hashCode(), emp1.hashCode());
//            log.info("equals {} {}", emp == emp1, emp.equals(emp1));

            int a1 = 100;
            int a2 = 100;
            Integer b1 = 100;
            Integer b2 = 100;
            log.info("== {} {} {} {}", a1 == a2, b1 == b2, a1 == b1, b2.equals(a2));

            int p1 = 128;
            int p2 = 128;
            Integer m1 = 128;
            Integer m2 = 128;
            log.info("== {} {} {} {}", p1 == p2, m1 == m2, p1 == m1, m2.equals(p2));

            String s1 = "143q";
            String s2 = "143q";
            String s3 = new String("143q");
            log.info("== {} {} {} {}", s1 == s2, s1.equals(s2), s2 == s3, s2.equals(s3));

        } catch (Exception e) {
            log.error("异常", e);
        }
    }

    /**
     * EnumHandler 普通代码块
     * EnumHandler 构造方法
     * EnumHandler 普通代码块
     * EnumHandler 构造方法
     * EnumHandler 普通代码块
     * EnumHandler 构造方法
     * EnumHandler 普通代码块
     * EnumHandler 构造方法     有多少成员就会调用多少次构造方法
     * EnumHandler 静态代码块    不论有多少成员静态方法只调用一次
     * 01
     * 02
     * 03
     * 04
     * EnumInnerHolder 静态代码块    静态私有内部类-静态代码块有且只会调用一次 适合单例模式
     * 壹
     * 贰
     * 叁
     * 肆
     */
    @Test
    public void testEnum() {
        System.out.println(EnumHandler.ONE.getCode());
        System.out.println(EnumHandler.TWO.getCode());
        System.out.println(EnumHandler.THREE.getCode());
        System.out.println(EnumHandler.FOUR.getCode());
        System.out.println(EnumHandler.getNameByCode(EnumHandler.ONE.getCode()));
        System.out.println(EnumHandler.getNameByCode(EnumHandler.TWO.getCode()));
        System.out.println(EnumHandler.getNameByCode(EnumHandler.THREE.getCode()));
        System.out.println(EnumHandler.getNameByCode(EnumHandler.FOUR.getCode()));
    }

    @Test
    public void testReflect() {
        try {
            // 反射获取实例
            Class<Employee> klass = Employee.class;
            Employee instance = klass.getDeclaredConstructor(String.class, String.class).newInstance("c1", "c1");
            log.info("反射调用构造方法,结果{}", instance);

            // 反射获取方法
            Method methodGetCode = klass.getDeclaredMethod("getCode");
            Object res1 = methodGetCode.invoke(instance);
            log.info("反射调用方法,结果{}", res1);

            // 反射获得字段
            Field fieldCode = klass.getDeclaredField("code");
            fieldCode.setAccessible(true);
            Object res2 = fieldCode.get(instance);
            log.info("反射获得字段,结果{}", res2);

        } catch (Exception e) {
            log.error("异常", e);
        }
    }

    /**
     * class.getResource getResourceAsStream
     * path由/开头    获得resources目录下的文件
     * path不由/开头    无法识别
     * classLoader.getResource getResourceAsStream
     * path由/开头    无法识别
     * path不由/开头   获得resources目录下的文件
     * contextClassLoader.getResource getResourceAsStream
     * path由/开头    无法识别
     * path不由/开头   获得resources目录下的文件
     */
    @Test
    public void testResourcePath() {
        try {
            Class<Employee> klass = Employee.class;
            ClassLoader klassLoader = klass.getClassLoader();
            ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
            String path1 = "/logback.xml";
            String path2 = "logback.xml";

            URL url1 = klass.getResource(path1);
            URL url2 = klass.getResource(path2);
            log.info("class.getResource[/][{}],class.getResource[{}]", null == url1 ? "" : url1.getPath(), null == url2 ? "" : url2.getPath());

            InputStream is1 = klass.getResourceAsStream(path1);
            InputStream is2 = klass.getResourceAsStream(path2);
            log.info("class.getResourceAsStream[/][{}],class.getResourceAsStream[{}]", null == is1 ? 0 : is1.available(), null == is2 ? 0 : is2.available());

            URL url3 = klassLoader.getResource(path1);
            URL url4 = klassLoader.getResource(path2);
            log.info("classLoader.getResource[/][{}],classLoader.getResource[{}]", null == url3 ? "" : url3.getPath(), null == url4 ? "" : url4.getPath());

            InputStream is3 = klassLoader.getResourceAsStream(path1);
            InputStream is4 = klassLoader.getResourceAsStream(path2);
            log.info("classLoader.getResourceAsStream[/][{}],classLoader.getResourceAsStream[{}]", null == is3 ? 0 : is3.available(), null == is4 ? 0 : is4.available());

            URL url5 = contextClassLoader.getResource(path1);
            URL url6 = contextClassLoader.getResource(path2);
            log.info("contextClassLoader.getResource[/][{}],contextClassLoader.getResource[{}]", null == url5 ? "" : url5.getPath(), null == url6 ? "" : url6.getPath());

            InputStream is5 = contextClassLoader.getResourceAsStream(path1);
            InputStream is6 = contextClassLoader.getResourceAsStream(path2);
            log.info("contextClassLoader.getResourceAsStream[/][{}],contextClassLoader.getResourceAsStream[{}]", null == is5 ? 0 : is5.available(), null == is6 ? 0 : is6.available());
        } catch (Exception e) {
            log.error("异常", e);
        }
    }
}