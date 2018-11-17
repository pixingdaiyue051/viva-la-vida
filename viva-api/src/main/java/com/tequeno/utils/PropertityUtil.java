package com.tequeno.utils;

import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class PropertityUtil {

	public static Properties loadPropertity(String path) {
		try {
			Properties pro = new Properties();
			ClassLoader cld = Thread.currentThread().getContextClassLoader();
			pro.load(cld.getResourceAsStream(path));
			return pro;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// 只加载某个值时，推荐使用该方法
	public static String getValue(String path, String key) {
		Properties p = loadPropertity(path);
		if (p != null)
			return p.getProperty(key);
		else
			return null;
	}

	// 需要连续加载配置文件中的数据时，推荐使用该方法
	public static Map<String, String> getMap(String path) {
		try {
			Properties p = loadPropertity(path);
			if (p != null) {
				Set<Object> keys = p.keySet();
				Map<String, String> map = new HashMap<String, String>();
				for (Object key : keys) {
					String value = p.getProperty(key.toString());
					map.put(key.toString(), value);
				}
				return map;
			} else
				return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void writeProperties(String path, Properties properties, String comment) {
		try {
			ClassLoader cld = Thread.currentThread().getContextClassLoader();
			FileOutputStream fos = new FileOutputStream(cld.getResource(path).getPath());
			properties.store(fos, comment);
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
