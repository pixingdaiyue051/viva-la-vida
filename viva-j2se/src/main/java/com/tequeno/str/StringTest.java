package com.tequeno.str;

import java.util.ArrayList;
import java.util.List;

public class StringTest {
	public static void main(String[] args) {
//		stringBufferOneDemo();
//		stringBuildOneDemo();
		getMaxLengthSubString("qwertyuiopasdfghjklzxcvbnm");
	}

	public static void stringBufferOneDemo() {
		StringBuffer buf = new StringBuffer();
		buf.append("1");
		buf.append("2");
		buf.append("3");
		buf.append("4");
		buf.append("5");
		buf.delete(0, 1);
		buf.replace(0, 1, "1");
		System.out.println(buf);
	}

	public static void stringBuildOneDemo() {
		StringBuilder builder = new StringBuilder();
		builder.append("1");
		builder.append("2");
		builder.append("3");
		builder.append("4");
		builder.append("5");
		builder.delete(0, 1);
		builder.replace(0, 0, "1");
		System.out.println(builder);
	}

	public static String getMaxLengthSubString(String s) {
		String result = null;
		char[] ch = s.toCharArray();
		List<String> list = new ArrayList<>();
		StringBuilder b = new StringBuilder();
		for (int i = 0; i < ch.length; i++) {
			for (int j = i; j < ch.length - 1; j++) {
				if (b.indexOf(String.valueOf(ch[j])) < 0) {
					b.append(ch[j]);
				} else {
					break;
				}
			}
			list.add(b.toString());
			b.delete(0, b.length());
		}
		int max = 0;
		if (!list.isEmpty()) {
			int len = 0;
			for (String str : list) {
				len = str.length();
				if (max < len) {
					max = len;
					result = str;
				}
			}
		}
		return result;
	}
}
