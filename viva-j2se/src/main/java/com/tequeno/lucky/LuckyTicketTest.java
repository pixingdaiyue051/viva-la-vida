package com.tequeno.lucky;

import java.io.*;

public class LuckyTicketTest {

    private final String PATH = System.getProperty("user.dir") + "\\doc\\luckyticket\\2\\ticket-";

    private final String SUB = ".txt";

    private final int GENERATE_NUM = 5;

    public static void main(String[] args) {
        LuckyTicketTest l = new LuckyTicketTest();
//        l.generateMyPocket();
        l.chenckForMyLuck();
    }

    public void generateMyPocket() {
        ObjectOutputStream oos = null;
        FileOutputStream fos = null;
        try {
            TicketGenerator tg = new TicketGenerator();
            for (int i = 0; i < GENERATE_NUM; i++) {
                Ticket tickt = tg.generateMyLuckyNum();
                System.out.println(tickt.show());
                fos = new FileOutputStream(PATH + (i + 1) + SUB);
                oos = new ObjectOutputStream(fos);
                oos.writeObject(tickt);
                oos.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void chenckForMyLuck() {
        ObjectInputStream ois = null;
        FileInputStream fis = null;
        try {
            TicketCheck tc = new TicketCheck();
            for (int i = 0; i < GENERATE_NUM; i++) {
                fis = new FileInputStream(PATH + (i + 1) + SUB);
                ois = new ObjectInputStream(fis);
                Ticket my = (Ticket) ois.readObject();
                my.resetSort();
                System.out.println("第" + (i + 1) + "注是:");
                System.out.println(my.show() + "**********" + tc.check(my));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
