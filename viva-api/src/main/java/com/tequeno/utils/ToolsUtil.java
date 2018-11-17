package com.tequeno.utils;

import org.apache.commons.lang.CharUtils;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public class ToolsUtil {
    public static String propertyToField(String property) {
        if (StringUtils.isBlank(property)) {
            return "";
        }
        char[] chars = property.toCharArray();
        StringBuffer sb = new StringBuffer();
        for (char c : chars) {
            if (CharUtils.isAsciiAlphaUpper(c)) {
                sb.append("_" + StringUtils.lowerCase(CharUtils.toString(c)));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static String fieldToProperty(String field) {
        if (StringUtils.isBlank(field)) {
            return "";
        }
        char[] chars = field.toCharArray();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (c == '_') {
                int j = i + 1;
                if (j < chars.length) {
                    sb.append(StringUtils.upperCase(CharUtils.toString(chars[j])));
                    i++;
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static Map<String, Object> refreshI18n(String language) {
        Locale locale = null;
        switch (language) {
            case StatesUtil.FT:
                locale = Locale.TAIWAN;
                break;
            case StatesUtil.YW:
                locale = Locale.US;
                break;
            default:
                locale = Locale.CHINA;
                break;
        }
        ResourceBundle rb = ResourceBundle.getBundle(ConstantsUtil.LAN_DESC, locale);
        Enumeration<String> keys = rb.getKeys();
        Map<String, Object> map = new HashMap<String, Object>();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            map.put(key, rb.getObject(key));
        }
        return map;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static void setAllFieldNull(Object o) {
        try {
            Class clz = o.getClass();
            Field[] fields = clz.getDeclaredFields();
            for (Field field : fields) {
                String fieldName = field.getName();
                if ("serialVersionUID".equals(fieldName))
                    continue;
                String methodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                Method d = clz.getDeclaredMethod(methodName, field.getType());
                field = null;
                d.invoke(o, field);
            }
            clz = clz.getSuperclass();
            fields = clz.getDeclaredFields();
            for (Field field : fields) {
                String fieldName = field.getName();
                if ("serialVersionUID".equals(fieldName))
                    continue;
                String methodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                Method d = clz.getDeclaredMethod(methodName, field.getType());
                field = null;
                d.invoke(o, field);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}