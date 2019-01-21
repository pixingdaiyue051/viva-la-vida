package com.tequeno.lucky;

import java.io.Serializable;
import java.util.Arrays;

public class Ticket implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8830926936916266143L;

	private String red1;
	private String red2;
	private String red3;
	private String red4;
	private String red5;
	private String red6;
	private String blue;

	public String getRed1() {
		return red1;
	}

	public void setRed1(String red1) {
		this.red1 = red1;
	}

	public String getRed2() {
		return red2;
	}

	public void setRed2(String red2) {
		this.red2 = red2;
	}

	public String getRed3() {
		return red3;
	}

	public void setRed3(String red3) {
		this.red3 = red3;
	}

	public String getRed4() {
		return red4;
	}

	public void setRed4(String red4) {
		this.red4 = red4;
	}

	public String getRed5() {
		return red5;
	}

	public void setRed5(String red5) {
		this.red5 = red5;
	}

	public String getRed6() {
		return red6;
	}

	public void setRed6(String red6) {
		this.red6 = red6;
	}

	public String getBlue() {
		return blue;
	}

	public void setBlue(String blue) {
		this.blue = blue;
	}
	
	public void resetSort() {
		Object[] objs = { red1, red2, red3, red4, red5, red6 };
		Arrays.sort(objs);
		setRed1(objs[0].toString());
		setRed2(objs[1].toString());
		setRed3(objs[2].toString());
		setRed4(objs[3].toString());
		setRed5(objs[4].toString());
		setRed6(objs[5].toString());
	}

	public String show() {
		return red1+" "+red2+" "+red3+" "+red4+" "+red5+" "+red6+"+"+blue;
		// StringBuilder bulider = new StringBuilder();
		// for (Object o : objs) {
		// bulider.append(o);
		// bulider.append(" ");
		// }
		// return bulider.deleteCharAt(bulider.length() - 1).append("+").append(blue).toString();
	}

	public static boolean checkRed(String checkNum, Ticket luckyTicket) {
		return checkNum.equals(luckyTicket.getRed1()) || checkNum.equals(luckyTicket.getRed2())
				|| checkNum.equals(luckyTicket.getRed3()) || checkNum.equals(luckyTicket.getRed4())
				|| checkNum.equals(luckyTicket.getRed5()) || checkNum.equals(luckyTicket.getRed6());
	}

	public static boolean checkBlue(String checkNum, Ticket luckyTicket) {
		return checkNum.equals(luckyTicket.getBlue());
	}
}
