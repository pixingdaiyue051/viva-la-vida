package com.tequeno.test;

import com.tequeno.inet.InetConst;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Test {

    public static void main(String[] args) {

//        EvaluateHandler handler = new EvaluateHandler();
//        String str = "robot,*obot,r**ot,r***t,r****,robot,*obot,r**ot,r***t,r****";
//
//        long l1 = System.currentTimeMillis();
//        String evaluateStr = handler.evaluate(str);
//        System.out.println(evaluateStr);
//        long l2 = System.currentTimeMillis();
//        System.out.println(l2 - l1);
//
//        long l3 = System.currentTimeMillis();
//        String evaluateBuf = handler.evaluateBuf(str);
//        System.out.println(evaluateBuf);
//        long l4 = System.currentTimeMillis();
//        System.out.println(l4 - l3);


//        Decent de = new Decent();
//        for (int i = 0; i < 1000; i++) {
//            System.out.println(de.sumReciprocal(i + 1));
//        }
//        System.out.println(de.sumEqualDifference(100));
//        System.out.println(de.sumEqualRatio(10));
//        de.sumFibonacci(10);

        Test test = new Test();
//        test.testIp();
        test.testScanner();

    }

    private void testIp() {
        try {
            InetAddress i = InetAddress.getLocalHost();
            System.out.println(i.toString());

            InetAddress ia = InetAddress.getLoopbackAddress();
            System.out.println(ia.toString());

            InetAddress iaa = InetAddress.getByName("LAPTOP-8KA88UT5");
            System.out.println(iaa.toString());

            InetAddress iaaa = InetAddress.getByName("www.baidu.com");
            System.out.println(iaaa.toString());

            InetAddress[] addrs = InetAddress.getAllByName("www.baidu.com");
            for (InetAddress inetAddress : addrs) {
                System.out.println(inetAddress.toString());
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    private void testScanner() {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                String nextLine = scanner.nextLine();
                if (InetConst.BREAK_OUT.equals(nextLine)) {
                    System.out.println("结束");
                    break;
                }
                System.out.println("输入:" + nextLine);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}