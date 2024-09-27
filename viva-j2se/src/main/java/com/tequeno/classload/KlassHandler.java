package com.tequeno.classload;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;

public class KlassHandler {

    private static final Logger log = LoggerFactory.getLogger(KlassHandler.class);

    public class InnerClass {
        public void run() {
            System.out.println("InnerClass run");
        }
    }

    public static class InnerStaticClass {
        public void run() {
            System.out.println("InnerStaticClass run");
        }
    }

    public void run() {
        System.out.println("KlassHandler run");
    }

    public void reflect() {
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
     * class.getResource
     *      path由/开头    获得resources目录下的文件
     *      path不由/开头    获得class当前目录下的文件
     * class.getResourceAsStream 在getResource之后再调用url.openStream
     *      path由/开头    无法识别
     *      path不由/开头    获得resources目录下的文件
     */
    public void resourcePath() {
        try {
            Class<Employee> klass = Employee.class;
            ClassLoader klassLoader = klass.getClassLoader();

            String path1 = "/logback.xml";
            URL url1 = klass.getResource(path1);
            InputStream is1 = klass.getResourceAsStream(path1);

            String path2 = "logback.xml";
            URL url2 = klassLoader.getResource(path2);
            InputStream is2 = klassLoader.getResourceAsStream(path2);


            String path3 = "logback.xml";
            ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
            URL url3 = contextClassLoader.getResource(path3);
            InputStream is3 = contextClassLoader.getResourceAsStream(path3);

            log.info("klass加载路径[{}],klassLoader加载路径[{}],Thread-contextClassLoader加载路径[{}]", url1.getPath(), url2.getPath(), url3.getPath());
        } catch (Exception e) {
            log.error("异常", e);
        }
    }

    public static void main(String[] args) {
        String o1 = args[0];
        if (null == o1 || o1.isEmpty()) {
            log.info("parameter 1 is null");
            return;
        }
        String o2 = args[1];
        if (null == o2 || o2.isEmpty()) {
            log.info("parameter 2 is null");
            return;
        }
        log.info("com.tequeno.classload.KlassHandler finished");
    }
}