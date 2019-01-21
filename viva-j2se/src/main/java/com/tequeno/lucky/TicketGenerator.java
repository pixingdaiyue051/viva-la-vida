package com.tequeno.lucky;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Random;

public class TicketGenerator {
	private final Ticket ticket = new Ticket();

	private final Random random = new Random();

	private final int MAX_RED = 33;
	private final int MAX_BLUE = 16;

	private final int MAX_RED_NUM = 6;

	private ArrayList<String> redNums = new ArrayList<>();

	private ArrayList<String> blueNums = new ArrayList<>();

	public TicketGenerator() {
		refillRednums();
		String blue = null;
		for (int i = 1; i <= MAX_BLUE; i++) {
			if (i < 10) {
				blue = "0" + i;
			} else {
				blue = String.valueOf(i);
			}
			blueNums.add(blue);
		}
	}

	private void refillRednums() {
		redNums.clear();
		String red = null;
		for (int i = 1; i <= MAX_RED; i++) {
			if (i < 10) {
				red = "0" + i;
			} else {
				red = String.valueOf(i);
			}
			redNums.add(red);
		}
	}

	private String generateRed() {
		int redIndex = 0;
		String redNum = null;
		do {
			redIndex = random.nextInt(MAX_RED);
			redNum = redNums.get(redIndex);
		} while (redNum == null);
		redNums.set(redIndex, null);
		return redNum;
	}

	private String generateBlue() {
		return blueNums.get(random.nextInt(MAX_BLUE));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Ticket generateMyLuckyNum() {
		try {
			Class clz = ticket.getClass();
			StringBuilder methodName = new StringBuilder();
			Method method = null;
			for (int i = 0; i < MAX_RED_NUM; i++) {
				methodName.append("setRed" + (i + 1));
				method = clz.getDeclaredMethod(methodName.toString(), String.class);
				method.invoke(ticket, generateRed());
				methodName.delete(0, methodName.length());
			}
			ticket.setBlue(generateBlue());
			ticket.resetSort();
			refillRednums();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ticket;
	}
}
