package com.tequeno.server;

import ocean.anno.MyAutowired;
import ocean.anno.MyComponent;

import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ScanUtil {

    public static Map<String, Object> map = new HashMap<>();

    public static void doScan() {
        try {
//            加载包路径
            String path = "D:\\files\\work\\2\\mybatis-ocean\\src\\main\\java\\com\\tequeno";
            File pathFile = new File(path);
            String[] pathFileNames = pathFile.list();
//            加载类路径
            for (String pathFileName : pathFileNames) {
                File classFile = new File(path + "\\" + pathFileName);
                String[] classFileNames = classFile.list();
                for (String classFileName : classFileNames) {
                    classFileName = classFileName.substring(0, classFileName.lastIndexOf("."));
                    Class clazz = Class.forName("com.tequeno." + pathFileName + "." + classFileName);
                    if (clazz.isAnnotationPresent(MyComponent.class)) {
//                        容器中已存在该bean，无需添加
                        if (map.containsKey(clazz.getSimpleName())) {
                            continue;
                        }
//                        可以反射获得该类对象，并存入容器
                        Object o = clazz.newInstance();
                        // 获得所有属性
                        Field[] fields = clazz.getDeclaredFields();
                        for (Field field : fields) {
//                            该属性需要自动注入
                            if (field.isAnnotationPresent(MyAutowired.class)) {
                                Object oI = null;
//                                如果容器内已有，则从容器获取
                                if (map.containsKey(field.getType().getSimpleName())) {
                                    oI = map.get(field.getType().getSimpleName());
                                } else {
//                                    容器内没有该bean，创建之后加入容器
                                    oI = Class.forName(field.getType().getName()).newInstance();
                                    map.put(field.getType().getSimpleName(), oI);
                                }
                                field.setAccessible(true);
                                field.set(o, oI);
                            }
                        }
//                        将bean加入容器中
                        map.put(clazz.getSimpleName(), o);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
