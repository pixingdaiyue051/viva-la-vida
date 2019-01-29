package org.util.xml;

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class XMLUtil {

	private static final String ENCODE = "UTF-8";
	private static final String SUB = ".xml";

	public static Document getDocument(String rootName, String xmlName) throws Exception {
		Document doc = DocumentHelper.createDocument();
		doc.setXMLEncoding(ENCODE);
		doc.setName(xmlName);
		doc.addElement(rootName);
		return doc;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void addElementForDocment(Document doc, Object o) throws Exception {
		// 获得类名
		Class clazz = o.getClass();
		XmlBeanElement xmlAnnotation = (XmlBeanElement) clazz.getAnnotation(XmlBeanElement.class);
		String annoName = xmlAnnotation.name();
		String annoWrapper = xmlAnnotation.wraper();
		Element root = doc.getRootElement();
		Element second = null;
		Element third = null;
		if (StringUtils.isNotBlank(annoWrapper)) {
			second = root.addElement(annoWrapper);
			third = second.addElement(annoName);
		} else {
			third = root.addElement(annoName);
		}
		// 获得实体类的所有属性
		Field[] properties = clazz.getDeclaredFields();
		if (properties != null && properties.length > 0) {
			for (Field field : properties) {
				String nodeName = field.getName();
				if (field.isAnnotationPresent(XmlFieldElement.class)) {
					XmlFieldElement xmlFieldElement = field.getAnnotation(XmlFieldElement.class);
					if (!xmlFieldElement.visible()) {
						continue;
					}
					if (StringUtils.isNotBlank(xmlFieldElement.name())) {
						nodeName = xmlFieldElement.name();
					}
				}
				// 反射get方法
				Method meth = clazz.getMethod(
						"get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1));
				Element fourth = third.addElement(nodeName);
				Object obj = meth.invoke(o);
				if (null == obj)
					fourth.setText("");
				else
					fourth.setText(obj.toString());
			}
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void addElementForDocment(Document doc, Object o, String elementName) throws Exception {
		// 获得类名
		Class clazz = o.getClass();
		XmlBeanElement xmlAnnotation = (XmlBeanElement) clazz.getAnnotation(XmlBeanElement.class);
		Element root = doc.getRootElement();
		Element second = null;
		Element third = null;
		if (StringUtils.isNotBlank(elementName)) {
			second = root.element(elementName);
			third = second.addElement(xmlAnnotation.name());
		} else {
			third = root.addElement(xmlAnnotation.name());
		}
		// 获得实体类的所有属性
		Field[] properties = clazz.getDeclaredFields();
		if (properties != null && properties.length > 0) {
			for (Field field : properties) {
				String nodeName = field.getName();
				if (field.isAnnotationPresent(XmlFieldElement.class)) {
					XmlFieldElement xmlFieldElement = field.getAnnotation(XmlFieldElement.class);
					if (!xmlFieldElement.visible()) {
						continue;
					}
					if (StringUtils.isNotBlank(xmlFieldElement.name())) {
						nodeName = xmlFieldElement.name();
					}
				}
				// 反射get方法
				Method meth = clazz.getMethod(
						"get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1));
				Element fourth = third.addElement(nodeName);
				Object obj = meth.invoke(o);
				if (null == obj)
					fourth.setText("");
				else
					fourth.setText(obj.toString());
			}
		}
	}

	public static String generateXml(Document doc, boolean needParseToFile, String destPath) throws Exception {
		if (!needParseToFile) {
			return doc.asXML();
		}
		// 获得文件
		File file = new File(destPath);
		if (!file.exists()) {
			file.mkdirs();
		}
		// 生成XML文件
		// 声明写XML的对象
		XMLWriter writer = null;
		OutputFormat format = OutputFormat.createPrettyPrint();
		// 设置XML文件的编码格式
		format.setEncoding(ENCODE);
		file = new File(destPath + "/" + doc.getName() + SUB);
		writer = new XMLWriter(new FileWriter(file), format);
		writer.setEscapeText(true);
		writer.write(doc);
		writer.close();
		return doc.asXML();
	}

	@SuppressWarnings({ "rawtypes" })
	public static Map<String, Object> parseXmlToMap(String path) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		SAXReader reader = new SAXReader();
		Document doc = reader.read(new File(path));
		Element root = doc.getRootElement();
		for (Iterator iterator = root.elementIterator(); iterator.hasNext();) {
			Element e = (Element) iterator.next();
			List list = e.elements();
			if (list.size() > 0) {
				map.put(e.getName(), listElement(e));
			} else
				map.put(e.getName(), e.getText());
		}
		return map;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static Map listElement(Element e) throws Exception {
		Map map = new HashMap();
		List list = e.elements();
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Element iter = (Element) list.get(i);
				List mapList = new ArrayList();

				if (iter.elements().size() > 0) {
					Map m = listElement(iter);
					if (map.get(iter.getName()) != null) {
						Object obj = map.get(iter.getName());
						if (!obj.getClass().getName().equals("java.util.ArrayList")) {
							mapList = new ArrayList();
							mapList.add(obj);
							mapList.add(m);
						}
						if (obj.getClass().getName().equals("java.util.ArrayList")) {
							mapList = (List) obj;
							mapList.add(m);
						}
						map.put(iter.getName(), mapList);
					} else
						map.put(iter.getName(), m);
				} else {
					if (map.get(iter.getName()) != null) {
						Object obj = map.get(iter.getName());
						if (!obj.getClass().getName().equals("java.util.ArrayList")) {
							mapList = new ArrayList();
							mapList.add(obj);
							mapList.add(iter.getText());
						}
						if (obj.getClass().getName().equals("java.util.ArrayList")) {
							mapList = (List) obj;
							mapList.add(iter.getText());
						}
						map.put(iter.getName(), mapList);
					} else
						map.put(iter.getName(), iter.getText());
				}
			}
		} else {
			map.put(e.getName(), e.getText());
		}
		return map;
	}

	public static void main(String[] args) {
		long l1 = System.currentTimeMillis();
		try {
			OutHeadBean head = new OutHeadBean();
			head.setCU("111");
			head.setBizDate("111");
			head.setDescription("111");
			head.setNumber("111");
			head.setOperaterName("111");
			List<OutBodyBean> list = new ArrayList<OutBodyBean>();
			for (int i = 0; i < 5; i++) {
				OutBodyBean body = new OutBodyBean();
				body.setSeq(i + 1);
				body.setFiMaterialType("111");
				body.setDepartment("111");
				body.setEquipmentType("111");
				body.setIssueType("111");
				body.setPrice(new BigDecimal(321));
				body.setTaxRate(new BigDecimal(321));
				body.setPriceInTax(new BigDecimal(321));
				body.setQty(new BigDecimal(321));
				body.setAmount(new BigDecimal(321));
				body.setNoTaxAmount(new BigDecimal(321));
				list.add(body);
			}
			Document doc = getDocument("root", "my_test");
			addElementForDocment(doc, head);
			addElementForDocment(doc, new Entries());
			String eName = Entries.class.getAnnotation(XmlBeanElement.class).name();
			for (OutBodyBean outBodyBean : list) {
				addElementForDocment(doc, outBodyBean, eName);
			}
			String str = generateXml(doc, true, "E:/Files/hexk/test");
			System.out.println(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		long l2 = System.currentTimeMillis();
		System.err.println("用时:" + (l2 - l1) + "ms");
	}
}
