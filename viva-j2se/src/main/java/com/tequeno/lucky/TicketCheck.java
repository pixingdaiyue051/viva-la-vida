package com.tequeno.lucky;

import java.lang.reflect.Method;
import java.util.Scanner;

public class TicketCheck {

    private final Ticket luckyTicket = new Ticket();

    private final Scanner scan = new Scanner(System.in);

    private final int MAX_RED_NUM = 6;

    private final String REG = "[\\sS]+";

    private int redCount = 0;

    private int blueCount = 0;

    public TicketCheck() {
        fillLucyTicket();
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public String check(Ticket my) {
        try {
            Class clz = my.getClass();
            StringBuilder methodName = new StringBuilder();
            Method method = null;
            for (int i = 0; i < MAX_RED_NUM; i++) {
                methodName.append("getRed" + (i + 1));
                method = clz.getDeclaredMethod(methodName.toString());
                if (Ticket.checkRed(method.invoke(my).toString(), luckyTicket)) {
                    redCount++;
                }
                methodName.delete(0, methodName.length());
            }
            if (Ticket.checkBlue(my.getBlue(), luckyTicket)) {
                blueCount++;
            }
            return check();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            redCount = 0;
            blueCount = 0;
        }
    }

    private String check() {
        String desc = redCount + "+" + blueCount;
        Rules[] rules = Rules.values();
        for (Rules r : rules) {
            if (r.getDesc().equals(desc)) {
                return r.getResult();
            }
        }
        return null;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private void fillLucyTicket() {
        String inStr = null;
        String[] strs = {};
        do {
            System.out.println("请输入" + (MAX_RED_NUM + 1) + "个中奖号码(以空格隔开):");
            inStr = scan.nextLine();
            strs = inStr.split(REG);
        } while (strs.length != (MAX_RED_NUM + 1));
        try {
            Class clz = luckyTicket.getClass();
            StringBuilder methodName = new StringBuilder();
            Method method = null;
            for (int i = 0; i < MAX_RED_NUM; i++) {
                methodName.append("setRed" + (i + 1));
                method = clz.getDeclaredMethod(methodName.toString(), String.class);
                method.invoke(luckyTicket, strs[i]);
                methodName.delete(0, methodName.length());
            }
            luckyTicket.setBlue(strs[MAX_RED_NUM]);
            luckyTicket.resetSort();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
