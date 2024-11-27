package org.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.CharUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.teuqueno.entity.sys.UmUserInfo;

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

	public static UmUserInfo getCurrentUser() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		HttpSession session = request.getSession();
		return (UmUserInfo) session.getAttribute(ConstantsUtil.USER_SESSION);
	}

	public static SolrInputDocument castToSolrInputDoc(Object o) {
		SolrInputDocument solrInputDoc = new SolrInputDocument();
		try {
			reflectGetField(o, solrInputDoc);
		} catch (Exception e) {
			e.printStackTrace();
			solrInputDoc = null;
		}
		return solrInputDoc;
	}

	@SuppressWarnings({ "rawtypes" })
	public static List<SolrInputDocument> castToSolrInputDocList(List objs) {
		System.out.println(objs.getClass());
		List<SolrInputDocument> solrInputDocList = new ArrayList<SolrInputDocument>();
		try {
			Object obj = objs.get(0);
			if (obj instanceof Map) {
				for (Object o : objs) {
					SolrInputDocument solrInputDoc = new SolrInputDocument();
					reflectGetField((Map) o, solrInputDoc);
					solrInputDocList.add(solrInputDoc);
				}
			} else {
				for (Object o : objs) {
					SolrInputDocument solrInputDoc = new SolrInputDocument();
					reflectGetField(o, solrInputDoc);
					solrInputDocList.add(solrInputDoc);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			solrInputDocList = null;
		}
		return solrInputDocList;
	}

	@SuppressWarnings("rawtypes")
	public static SolrInputDocument castToSolrInputDoc(Map map) {
		SolrInputDocument solrInputDoc = new SolrInputDocument();
		try {
			reflectGetField(map, solrInputDoc);
		} catch (Exception e) {
			e.printStackTrace();
			solrInputDoc = null;
		}
		return solrInputDoc;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static void reflectGetField(Object o, SolrInputDocument solrInputDoc) throws Exception {
		Class clz = o.getClass();
		Field[] fields = clz.getDeclaredFields();
		for (Field field : fields) {
			String fieldName = field.getName();
			if ("serialVersionUID".equals(fieldName))
				continue;
			String methodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
			Method d = clz.getDeclaredMethod(methodName);
			solrInputDoc.addField(fieldName, d.invoke(o));
		}
		clz = clz.getSuperclass();
		fields = clz.getDeclaredFields();
		for (Field field : fields) {
			String fieldName = field.getName();
			if ("serialVersionUID".equals(fieldName))
				continue;
			String methodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
			Method d = clz.getDeclaredMethod(methodName);
			solrInputDoc.addField(fieldName, d.invoke(o));
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static void reflectGetField(Map map, SolrInputDocument solrInputDoc) throws Exception {
		Set<Entry> set = map.entrySet();
		for (Entry entry : set) {
			solrInputDoc.addField(entry.getKey().toString(), entry.getValue());
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
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