package com.tequeno;

import com.tequeno.algorithm.Decent;
import com.tequeno.algorithm.EvaluateHandler;
import com.tequeno.algorithm.IdHandler;
import com.tequeno.net.InetConst;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Scanner;

public class AlgorithmTest {

    @Test
    public void testDecent() {

        Decent de = new Decent();
        for (int i = 0; i < 1000; i++) {
            System.out.println(de.sumReciprocal(i + 1));
        }
        System.out.println(de.sumEqualDifference(100));
        System.out.println(de.sumEqualRatio(10));
        de.sumFibonacci(10);
    }

    @Test
    public void testEval() {

        EvaluateHandler handler = new EvaluateHandler();
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

        final String max = handler.getMaxLengthSubString("qwertyuiopasdfghjklzxcvbnm");
        System.out.println(max);

        int sec = 0;

        System.out.println(handler.exchangeSec(sec));

        sec = 59;
        System.out.println(handler.exchangeSec(sec));

        sec = 61;
        System.out.println(handler.exchangeSec(sec));

        sec = 3599;
        System.out.println(handler.exchangeSec(sec));

        sec = 3601;
        System.out.println(handler.exchangeSec(sec));

        sec = 86399;
        System.out.println(handler.exchangeSec(sec));

        sec = 86401;
        System.out.println(handler.exchangeSec(sec));
    }


    @Test
    public void testId() {

        String idCard = "632323190605261343";
        idCard = "510921197912177590";
        idCard = "510704198205292714";
        boolean check = IdHandler.check(idCard);
        System.out.println(check);

        String idCard1 = IdHandler.singleOne();
        System.out.println(idCard1);
        boolean check1 = IdHandler.check(idCard);
        System.out.println(check1);

        List<String> randomList = IdHandler.randomList(10);
        randomList.forEach(System.out::println);
    }


    @Test
    public void testIp() {
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

}