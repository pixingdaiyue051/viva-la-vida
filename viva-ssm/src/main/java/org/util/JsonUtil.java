package org.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtilsBean;

import com.alibaba.fastjson.JSON;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@SuppressWarnings({ "static-access", "rawtypes" })
public class JsonUtil {

	public static String toJsonString(Object o) {
		return JSON.toJSONString(o);
	}

	public static Map<String, String> parseProperties(String jsonStr) {
		if (jsonStr == null || jsonStr.length() < 0) {
			return null;
		}
		JSONObject jsonObj = null;
		Map<String, String> pros = new HashMap<String, String>();
		try {
			jsonObj = new JSONObject().fromObject(jsonStr);

			Iterator it = jsonObj.keys();
			while (it.hasNext()) {
				String key = it.next().toString();
				String value = jsonObj.getString(key);
				pros.put(key, value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pros;
	}

	public static Map<String, Object> parseToMap(String jsonStr) {
		if (jsonStr == null || jsonStr.length() < 0) {
			return null;
		}
		JSONObject jsonObj = null;
		Map<String, Object> pros = new HashMap<String, Object>();
		try {
			jsonObj = new JSONObject().fromObject(jsonStr);

			Iterator it = jsonObj.keys();
			while (it.hasNext()) {
				String key = it.next().toString();
				String value = jsonObj.getString(key);
				pros.put(key, value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pros;
	}

	public static List<Map<String, String>> getMapList(String jsonStr) {
		if (jsonStr == null || jsonStr.length() < 0) {
			return null;
		}
		JSONArray array = JSONArray.fromObject(jsonStr);
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		for (Iterator iter = array.iterator(); iter.hasNext();) {
			Map<String, String> pros = new HashMap<String, String>();
			JSONObject jsonObj = (JSONObject) iter.next();

			Iterator it = jsonObj.keys();
			while (it.hasNext()) {
				String key = it.next().toString();
				String value = jsonObj.getString(key);
				if (value != null && !"null".equals(value.toLowerCase())) {
					pros.put(key, value);
				}
			}
			list.add(pros);
		}
		return list;
	}

	public static Map<String, Map<String, String>> getMapByField(String jsonStr, String field) {
		if (jsonStr == null || jsonStr.length() < 0) {
			return null;
		}
		JSONArray array = JSONArray.fromObject(jsonStr);
		Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();
		for (Iterator iter = array.iterator(); iter.hasNext();) {
			String fieldValue = "";
			Map<String, String> pros = new HashMap<String, String>();
			JSONObject jsonObj = (JSONObject) iter.next();

			Iterator it = jsonObj.keys();
			while (it.hasNext()) {
				String key = it.next().toString();
				String value = jsonObj.getString(key);
				if (value != null && !"null".equals(value.toLowerCase())) {
					pros.put(key, value);
				}
				if (key.equals(field)) {
					fieldValue = value;
				}
			}
			map.put(fieldValue, pros);
		}
		return map;
	}

	public static <T> T maptoBean(Class<T> clazz, Map map) {
		T obj = null;
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
			obj = clazz.newInstance(); // 创建 JavaBean 对象

			// 给 JavaBean 对象的属性赋值
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (int i = 0; i < propertyDescriptors.length; i++) {
				PropertyDescriptor descriptor = propertyDescriptors[i];
				String propertyName = descriptor.getName();
				if (map.containsKey(propertyName)) {
					// 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。
					Object value = null;
					try {
						value = map.get(propertyName);
					} catch (Exception e) {
						e.printStackTrace();
					}
					if ("".equals(value)) {
						value = null;
					}
					Object[] args = new Object[1];
					args[0] = value;
					try {
						descriptor.getWriteMethod().invoke(obj, args);
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (IllegalAccessException e) {
			System.out.println("实例化 JavaBean 失败");
		} catch (IntrospectionException e) {
			System.out.println("分析类属性失败");
		} catch (IllegalArgumentException e) {
			System.out.println("映射错误");
		} catch (InstantiationException e) {
			System.out.println("实例化 JavaBean 失败");
		}
		return (T) obj;
	}

	public static Map<String, Object> beanToMap(Object obj) {
		Map<String, Object> params = new HashMap<String, Object>(0);
		try {
			PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
			PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(obj);
			for (int i = 0; i < descriptors.length; i++) {
				String name = descriptors[i].getName();
				if (!"class".equals(name)) {
					params.put(name, propertyUtilsBean.getNestedProperty(obj, name));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return params;
	}
}